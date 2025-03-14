package com.littleurl.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrlResponseDto {
    private String message;
    private String shortCode;
    private String shortUrl;
} 