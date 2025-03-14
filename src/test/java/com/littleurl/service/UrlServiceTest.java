package com.littleurl.service;

import com.littleurl.dto.response.ShortUrlResponseDto;
import com.littleurl.model.Url;
import com.littleurl.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

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
} 