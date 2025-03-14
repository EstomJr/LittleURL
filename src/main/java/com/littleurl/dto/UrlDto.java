package com.littleurl.dto;

import com.littleurl.entity.BaseEntity;
import com.littleurl.model.Url;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlDto extends BaseEntity{
    
    private String originalUrl;
    private String shortCode;
    private String shortUrl;
    private LocalDateTime expiresAt;
    private int accessCount;
    private String message;

    public Url toUrl() {
        Url url = new Url();
        url.setOriginalUrl(this.originalUrl);
        url.setShortUrl(this.shortUrl);
        url.setExpiresAt(this.expiresAt);
        url.setAccessCount(this.accessCount);
        url.setUpdatedAt(this.getUpdatedAt());
        url.setCreatedAt(this.getCreatedAt());
        return url;
    }

}
