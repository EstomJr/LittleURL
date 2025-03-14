package com.littleurl.service;

import com.littleurl.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShortCodeService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private final Random random = new Random();

    /**
     * Gera um código curto único.
     * @return Código curto de 6 caracteres.
     */
    public String generateShortCode() {
        StringBuilder shortCode = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            shortCode.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return shortCode.toString();
    }

    /**
     * Gera um código curto único que não existe no banco de dados.
     * @param urlRepository Repositório para verificar a existência do código.
     * @return Código curto único.
     */
    public String generateUniqueShortCode(UrlRepository urlRepository) {
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (urlRepository.existsByShortCode(shortCode));
        return shortCode;
    }
}
