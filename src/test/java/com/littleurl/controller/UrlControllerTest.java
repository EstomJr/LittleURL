package com.littleurl.controller;

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
        UrlStatsResponseDto expectedStats = new UrlStatsResponseDto(
            10,
            2.0,
            LocalDateTime.now()
        );
        
        when(statisticService.getUrlStats(anyString())).thenReturn(expectedStats);


        ResponseEntity<UrlStatsResponseDto> response = urlController.getUrlStats(shortCode);


        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedStats, response.getBody());
    }
} 