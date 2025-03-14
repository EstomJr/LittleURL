package com.littleurl.service;

import com.littleurl.dto.response.ShortUrlResponseDto;
import com.littleurl.dto.UrlDto;
import com.littleurl.exception.NotFoundException;
import com.littleurl.model.Statistic;
import com.littleurl.model.Url;
import com.littleurl.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private ShortCodeService shortCodeService;

    @Autowired
    private StatisticService statisticService;

    private Url url;

    @Value("${app.base-url}")
    private String baseUrl;

    private final String notFoundMessage = "Checar o ShortCode.";

    public ShortUrlResponseDto createShortUrl(String originalUrl) {
        Url url = new Url();
        String shortCode = shortCodeService.generateUniqueShortCode(urlRepository);
        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setShortUrl(buildShortUrl(shortCode));
        url.setExpiresAt(LocalDateTime.now().plusDays(30));
        url.setCreatedAt(LocalDateTime.now());
        url.setUpdatedAt(LocalDateTime.now());
        url.setAccessCount(0);
        urlRepository.save(url);
        
        return new ShortUrlResponseDto(
            "URL encurtada com sucesso!",
            shortCode,
            buildShortUrl(shortCode)
        );
    }

    public UrlDto getOriginalUrl(String shortCode) throws NotFoundException {
        Url url = urlRepository.findByShortCode(shortCode).orElse(null);
        if (url != null) {
            if (url.isExpired()) {
                throw new NotFoundException("a URL expirou.");
            }
            url.incrementAccessCount();
        } else {
            throw new NotFoundException(notFoundMessage);
        }
        return urlRepository.save(url).toDto();
    }

    public List<UrlDto> getAllUrls() {
        List<UrlDto> dtos = new ArrayList<>();
            dtos.add(url.toDto());
        return dtos;
    }

    private String buildShortUrl(String shortCode) {
        return baseUrl + "/" + shortCode;
    }

    public Statistic getStats() {
        return statisticService.getStats();
    }
}
