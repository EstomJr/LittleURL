package com.littleurl.service;

import com.littleurl.dto.response.UrlStatsResponseDto;
import com.littleurl.exception.NotFoundException;
import com.littleurl.model.Statistic;
import com.littleurl.model.Url;
import com.littleurl.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticService {

    @Autowired
    private UrlRepository urlRepository;

    public Statistic getStats() {
        LocalDateTime now = LocalDateTime.now();
        List<Url> allUrls = urlRepository.findAll();
        List<Url> activeUrls = allUrls.stream()
                .filter(url -> !url.isExpired())
                .collect(Collectors.toList());

        int totalAccessCount = allUrls.stream()
                .mapToInt(url -> url.getAccessCount() != null ? url.getAccessCount() : 0)
                .sum();

        List<UrlStatsResponseDto> activeUrlStats = activeUrls.stream()
                .map(url -> {
                    int accessCount = url.getAccessCount() != null ? url.getAccessCount() : 0;
                    LocalDateTime createdAt = url.getCreatedAt() != null ? url.getCreatedAt() : now;
                    long daysBetween = Math.max(1, ChronoUnit.DAYS.between(createdAt, now));
                    double averagePerDay = (double) accessCount / daysBetween;

                    UrlStatsResponseDto stats = new UrlStatsResponseDto(
                        accessCount,
                        Math.round(averagePerDay * 100.0) / 100.0,
                        createdAt
                    );
                    stats.setShortCode(url.getShortCode());
                    stats.setShortUrl(url.getShortUrl());
                    return stats;
                })
                .collect(Collectors.toList());

        Statistic stats = new Statistic();
        stats.setTotalShortenedUrls((long) allUrls.size());
        stats.setTotalRedirects((long) totalAccessCount);
        stats.setActiveUrls((long) activeUrls.size());
        stats.setExpiredUrls((long) (allUrls.size() - activeUrls.size()));
        stats.setActiveUrlStats(activeUrlStats);

        return stats;
    }

    public UrlStatsResponseDto getUrlStats(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new NotFoundException("URL não encontrada"));

        int accessCount = url.getAccessCount() != null ? url.getAccessCount() : 0;
        LocalDateTime createdAt = url.getCreatedAt() != null ? url.getCreatedAt() : LocalDateTime.now();
        long daysBetween = Math.max(1, ChronoUnit.DAYS.between(createdAt, LocalDateTime.now()));
        double averagePerDay = (double) accessCount / daysBetween;

        UrlStatsResponseDto stats = new UrlStatsResponseDto(
            accessCount,
            Math.round(averagePerDay * 100.0) / 100.0,
            createdAt
        );
        stats.setShortCode(url.getShortCode());
        stats.setShortUrl(url.getShortUrl());
        return stats;
    }
}
