package com.littleurl.service;

import com.littleurl.dto.response.UrlStatsResponseDto;
import com.littleurl.exception.NotFoundException;
import com.littleurl.model.Url;
import com.littleurl.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class StatisticServiceTest {

    @InjectMocks
    private StatisticService statisticService;

    @Mock
    private UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUrlStats_ShouldReturnStats() {
        String shortCode = "abc123";
        Url url = new Url();
        url.setAccessCount(10);
        LocalDateTime createdAt = LocalDateTime.now().minusDays(5);
        url.setCreatedAt(createdAt);
        url.setShortCode(shortCode);
        url.setShortUrl("http://localhost:8080/" + shortCode);
        
        when(urlRepository.findByShortCode(anyString())).thenReturn(Optional.of(url));

        UrlStatsResponseDto stats = statisticService.getUrlStats(shortCode);

        assertNotNull(stats);
        assertEquals(10, stats.getUrlAccessCount());
        assertEquals(2.0, stats.getAverageAccessesPerDay());
        assertEquals(createdAt, stats.getUrlCreatedAt());
        assertEquals(shortCode, stats.getShortCode());
        assertEquals("http://localhost:8080/" + shortCode, stats.getShortUrl());
    }

    @Test
    void getUrlStats_WhenUrlNotFound_ShouldThrowNotFoundException() {
        String shortCode = "nonexistent";
        when(urlRepository.findByShortCode(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> statisticService.getUrlStats(shortCode));
    }
} 