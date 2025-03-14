package com.littleurl.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Representa informações estatísticas sobre o serviço de encurtamento de URLs.")
public class Statistic {

    @Schema(description = "O número total de URLs encurtadas.", example = "150")
    private Long totalShortenedUrls;
    @Schema(description = "O número total de vezes que as URLs encurtadas foram acessadas.", example = "4500")
    private Long totalRedirects;
    @Schema(description = "O número total de URLs ativas.", example = "100")
    private Long activeUrls;
    @Schema(description = "O número total de URLs expiradas.", example = "50")
    private Long expiredUrls;
    @Schema(description = "Total de acessos da URL específica", example = "100")
    private Integer urlAccessCount;
    @Schema(description = "Média de acessos por dia da URL", example = "10.5")
    private Double averageAccessesPerDay;
    @Schema(description = "Data de criação da URL", example = "2024-03-20T10:00:00")
    private LocalDateTime urlCreatedAt;
}
