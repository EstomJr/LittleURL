package com.littleurl.controller;

import com.littleurl.dto.UrlDto;
import com.littleurl.exception.ExpiredUrlException;
import com.littleurl.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping
@Tag(
        name = "Controller de Redirecionamento",
        description = "Controller responsável por lidar com solicitações de redirecionamento. "
)
public class RedirectController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/{shortCode}")
    @Operation(
            summary = "Redirecionar para a URL Original",
            description = "Processa solicitações para redirecionar uma URL encurtada (shortCode) para sua URL original correspondente. " +
                    "Se a URL encurtada estiver expirada, uma exceção será lançada."
    )
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortCode) throws ExpiredUrlException {
        UrlDto originalUrl = urlService.getOriginalUrl(shortCode);

        if(originalUrl.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ExpiredUrlException();
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl.getOriginalUrl())).build();
    }
}
