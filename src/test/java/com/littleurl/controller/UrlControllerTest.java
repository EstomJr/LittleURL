package com.littleurl.controller;

import com.littleurl.dto.UrlDto;
import com.littleurl.dto.response.ShortUrlResponseDto;
import com.littleurl.dto.response.UrlStatsResponseDto;
import com.littleurl.model.Url;
import com.littleurl.service.StatisticService;
import com.littleurl.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UrlControllerTest {

    @InjectMocks
    private UrlController urlController;

    @Mock
    private UrlService urlService;

    @Mock
    private StatisticService statisticService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shortenUrl_ShouldReturnShortUrlResponse() {
        Url url = new Url();
        url.setOriginalUrl("https://www.example.com");
        
        ShortUrlResponseDto expectedResponse = new ShortUrlResponseDto(
            "URL encurtada com sucesso!",
            "abc123",
            "http://localhost:8080/abc123"
        );
        
        when(urlService.createShortUrl(anyString())).thenReturn(expectedResponse);

        ResponseEntity<ShortUrlResponseDto> response = urlController.shortenUrl(url);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getUrlStats_ShouldReturnStats() {
        String shortCode = "abc123";
        LocalDateTime now = LocalDateTime.now();
        UrlStatsResponseDto expectedStats = new UrlStatsResponseDto(10, 2.0, now);
        expectedStats.setShortCode(shortCode);
        expectedStats.setShortUrl("http://localhost:8080/" + shortCode);
        
        when(statisticService.getUrlStats(anyString())).thenReturn(expectedStats);

        ResponseEntity<UrlStatsResponseDto> response = urlController.getUrlStats(shortCode);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedStats, response.getBody());
    }

    @Test
    void getAllOriginalUrl_ShouldReturnListOfUrls() {
        // Arrange
        UrlDto url1 = new UrlDto();
        url1.setOriginalUrl("https://example1.com");
        url1.setShortCode("abc123");
        url1.setShortUrl("http://localhost:8080/abc123");
        url1.setExpiresAt(LocalDateTime.now().plusDays(30));
        url1.setAccessCount(5);

        UrlDto url2 = new UrlDto();
        url2.setOriginalUrl("https://example2.com");
        url2.setShortCode("def456");
        url2.setShortUrl("http://localhost:8080/def456");
        url2.setExpiresAt(LocalDateTime.now().plusDays(30));
        url2.setAccessCount(3);

        List<UrlDto> expectedUrls = Arrays.asList(url1, url2);
        when(urlService.getAllUrls()).thenReturn(expectedUrls);

        // Act
        ResponseEntity<List<UrlDto>> response = urlController.getAllOriginalUrl();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<UrlDto> actualUrls = response.getBody();
        assertNotNull(actualUrls);
        assertEquals(2, actualUrls.size());
        assertEquals(url1.getOriginalUrl(), actualUrls.get(0).getOriginalUrl());
        assertEquals(url1.getShortCode(), actualUrls.get(0).getShortCode());
        assertEquals(url1.getShortUrl(), actualUrls.get(0).getShortUrl());
        assertEquals(url1.getAccessCount(), actualUrls.get(0).getAccessCount());
        assertEquals(url2.getOriginalUrl(), actualUrls.get(1).getOriginalUrl());
        assertEquals(url2.getShortCode(), actualUrls.get(1).getShortCode());
        assertEquals(url2.getShortUrl(), actualUrls.get(1).getShortUrl());
        assertEquals(url2.getAccessCount(), actualUrls.get(1).getAccessCount());
    }
} 