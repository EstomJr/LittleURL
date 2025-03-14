package com.littleurl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.littleurl.dto.UrlDto;
import com.littleurl.entity.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "urls")
public class Url extends BaseEntity {

    @Id
    private String id;
    private String originalUrl;
    private String shortCode;
    private String shortUrl;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime expiresAt;
    
    private Integer accessCount = 0;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public void incrementAccessCount() {
        if (this.accessCount == null) {
            this.accessCount = 0;
        }
        this.accessCount++;
    }

    public UrlDto toDto() {
        UrlDto dto = new UrlDto();
        dto.setOriginalUrl(this.originalUrl);
        dto.setShortCode(this.shortCode);
        dto.setShortUrl(this.shortUrl);
        dto.setExpiresAt(this.expiresAt);
        dto.setAccessCount(this.accessCount != null ? this.accessCount : 0);
        dto.setUpdatedAt(this.getUpdatedAt());
        dto.setCreatedAt(this.getCreatedAt());
        return dto;
    }

}
