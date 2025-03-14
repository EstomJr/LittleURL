package com.littleurl.service;

import com.littleurl.dto.UrlDto;
import com.littleurl.dto.response.ShortUrlResponseDto;
import com.littleurl.model.Url;
import com.littleurl.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private ShortCodeService shortCodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(urlService, "baseUrl", "http://localhost:8080");
    }

    @Test
    void createShortUrl_ShouldReturnShortUrlResponse() {
        String originalUrl = "https://www.example.com";
        String shortCode = "abc123";
        when(shortCodeService.generateUniqueShortCode(any())).thenReturn(shortCode);
        when(urlRepository.save(any())).thenReturn(new Url());

        ShortUrlResponseDto response = urlService.createShortUrl(originalUrl);

        assertNotNull(response);
        assertEquals("URL encurtada com sucesso!", response.getMessage());
        assertEquals(shortCode, response.getShortCode());
        assertEquals("http://localhost:8080/" + shortCode, response.getShortUrl());
    }

    @Test
    void getAllUrls_ShouldReturnListOfUrls() {
        // Arrange
        Url url1 = new Url();
        url1.setOriginalUrl("https://example1.com");
        url1.setShortCode("abc123");
        url1.setShortUrl("http://localhost:8080/abc123");
        url1.setExpiresAt(LocalDateTime.now().plusDays(30));
        url1.setAccessCount(5);

        Url url2 = new Url();
        url2.setOriginalUrl("https://example2.com");
        url2.setShortCode("def456");
        url2.setShortUrl("http://localhost:8080/def456");
        url2.setExpiresAt(LocalDateTime.now().plusDays(30));
        url2.setAccessCount(3);

        when(urlRepository.findAll()).thenReturn(Arrays.asList(url1, url2));

        // Act
        List<UrlDto> result = urlService.getAllUrls();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(url1.getOriginalUrl(), result.get(0).getOriginalUrl());
        assertEquals(url1.getShortCode(), result.get(0).getShortCode());
        assertEquals(url1.getShortUrl(), result.get(0).getShortUrl());
        assertEquals(url1.getAccessCount(), result.get(0).getAccessCount());
        assertEquals(url2.getOriginalUrl(), result.get(1).getOriginalUrl());
        assertEquals(url2.getShortCode(), result.get(1).getShortCode());
        assertEquals(url2.getShortUrl(), result.get(1).getShortUrl());
        assertEquals(url2.getAccessCount(), result.get(1).getAccessCount());
    }
} 