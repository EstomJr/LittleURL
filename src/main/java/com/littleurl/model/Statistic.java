package com.littleurl.model;

import com.littleurl.dto.response.UrlStatsResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Schema(description = "Representa informações estatísticas sobre o serviço de encurtamento de URLs.")
@Document(collection = "statistic_url")
public class Statistic {

    @Schema(description = "O número total de URLs encurtadas.", example = "150")
    private Long totalShortenedUrls;
    @Schema(description = "O número total de vezes que as URLs encurtadas foram acessadas.", example = "4500")
    private Long totalRedirects;
    @Schema(description = "O número total de URLs ativas.", example = "100")
    private Long activeUrls;
    @Schema(description = "O número total de URLs expiradas.", example = "50")
    private Long expiredUrls;
    @Schema(description = "Lista de estatísticas das URLs ativas")
    private List<UrlStatsResponseDto> activeUrlStats;
}
