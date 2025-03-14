package com.littleurl.controller;

import com.littleurl.dto.UrlDto;
import com.littleurl.dto.response.ShortUrlResponseDto;
import com.littleurl.dto.response.UrlStatsResponseDto;
import com.littleurl.model.Statistic;
import com.littleurl.model.Url;
import com.littleurl.service.UrlService;
import com.littleurl.service.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/")
@CrossOrigin
@Tag(
        name = "URL Controller",
        description = "Controller responsável pelo encurtamento e gerenciamento de URLs. " +
                "Fornece endpoints para gerar URLs curtas, recuperar URLs originais, "

)
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Autowired
    private StatisticService statisticService;

    @PostMapping("/shorten")
    @Operation(
            summary = "Encurtar URL",
            description = "Gera um código curto único para uma URL original fornecida. " +
                    "Aceita a URL original no corpo da requisição e retorna uma representação da URL encurtada."
    )
    public ResponseEntity<ShortUrlResponseDto> shortenUrl(@RequestBody Url url) {
        ShortUrlResponseDto response = urlService.createShortUrl(url.getOriginalUrl());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortCode}")
    @Operation(
            summary = "Recuperar URL Original",
            description = "Busca a URL original correspondente ao código curto fornecido. " +
                    "Retorna os detalhes da URL se o código curto for válido."
    )
    public ResponseEntity<UrlDto> getOriginalUrl(@PathVariable String shortCode) {
        UrlDto originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.ok(originalUrl);
    }

    @GetMapping("/Listar")
    @Operation(
            summary = "Listar Todas as URLs",
            description = "Recupera todas as URLs originais e seus códigos curtos correspondentes armazenados no banco de dados."
    )
    public ResponseEntity<List<UrlDto>> getAllOriginalUrl() {
        List<UrlDto> urls = urlService.getAllUrls();
        return ResponseEntity.ok(urls);
    }

    @GetMapping("/stats")
    @Operation(
            summary = "Recuperar Estatísticas Globais",
            description = "Fornece informações estatísticas sobre o serviço de encurtamento de URLs, " +
                    "incluindo totais e detalhes de todas as URLs ativas.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estatísticas recuperadas com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Statistic.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor."
                    )
            }
    )
    public ResponseEntity<Statistic> getStats() {
        return ResponseEntity.ok(statisticService.getStats());
    }

    @GetMapping("/stats/{shortCode}")
    @Operation(
            summary = "Recuperar Estatísticas de URL Específica",
            description = "Fornece informações estatísticas sobre uma URL específica, " +
                    "incluindo total de acessos e média de acessos por dia.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estatísticas recuperadas com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UrlStatsResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "URL não encontrada."
                    )
            }
    )
    public ResponseEntity<UrlStatsResponseDto> getUrlStats(@PathVariable String shortCode) {
        return ResponseEntity.ok(statisticService.getUrlStats(shortCode));
    }
}
