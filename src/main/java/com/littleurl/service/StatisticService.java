package com.littleurl.service;

import com.littleurl.dto.response.UrlStatsResponseDto;
import com.littleurl.exception.NotFoundException;
import com.littleurl.exception.TotalAccessCountException;
import com.littleurl.model.Statistic;
import com.littleurl.model.Url;
import com.littleurl.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class StatisticService {

    @Autowired
    private UrlRepository urlRepository;

    public UrlStatsResponseDto getUrlStats(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new NotFoundException("URL não encontrada"));


        int accessCount = url.getAccessCount() != null ? url.getAccessCount() : 0;


        LocalDateTime createdAt = url.getCreatedAt() != null ? url.getCreatedAt() : LocalDateTime.now();


        LocalDateTime now = LocalDateTime.now();
        long daysBetween = Math.max(1, ChronoUnit.DAYS.between(createdAt, now));
        double averagePerDay = (double) accessCount / daysBetween;

        return new UrlStatsResponseDto(
            accessCount,
            Math.round(averagePerDay * 100.0) / 100.0,
            createdAt
        );
    }

    public Statistic getStats(){
        Statistic stats = new Statistic();

        Long quantUrls = urlRepository.count();
        stats.setTotalShortenedUrls(quantUrls);

        Long quantActiveUrls = urlRepository.countByExpiresAtAfter(LocalDateTime.now());
        stats.setActiveUrls(quantActiveUrls);

        Long quantExpiredUrls = urlRepository.countByExpiresAtBefore(LocalDateTime.now());
        stats.setExpiredUrls(quantExpiredUrls);

        stats.setTotalRedirects(totalAccessCount());

        return stats;
    }

    private Long totalAccessCount() {
        long totalAccess = 0L;
        try {
            for(Url u: urlRepository.findAll()) {
                totalAccess += u.getAccessCount();
            }
        } catch (TotalAccessCountException e) {
            throw new RuntimeException(e);
        }
        return totalAccess;
    }

}
