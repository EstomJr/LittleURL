package com.littleurl.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlStatsResponseDto {
    private Integer urlAccessCount = 0;
    private Double averageAccessesPerDay = 0.0;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime urlCreatedAt = LocalDateTime.now();
    
    private String shortCode;
    private String shortUrl;
    
    public UrlStatsResponseDto(Integer urlAccessCount, Double averageAccessesPerDay, LocalDateTime urlCreatedAt) {
        this.urlAccessCount = urlAccessCount;
        this.averageAccessesPerDay = averageAccessesPerDay;
        this.urlCreatedAt = urlCreatedAt;
    }
} 