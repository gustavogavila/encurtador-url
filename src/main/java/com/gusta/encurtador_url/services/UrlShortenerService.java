package com.gusta.encurtador_url.services;

import com.gusta.encurtador_url.dto.ShortenUrlResponse;
import com.gusta.encurtador_url.entities.ShortenedUrl;
import com.gusta.encurtador_url.repositories.ShortenedUrlRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UrlShortenerService {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final String BASE_URL = "https://short.local/";
    private static final int MAX_RETRIES = 10;

    private final ShortenedUrlRepository repository;
    private final SecureRandom random;

    public UrlShortenerService(ShortenedUrlRepository repository) {
        this.repository = repository;
        this.random = new SecureRandom();
    }

    public ShortenUrlResponse shortenUrl(String originalUrl) {
        String shortCode = generateUniqueShortCode();
        ShortenedUrl entity = new ShortenedUrl(shortCode, originalUrl);
        repository.save(entity);

        String shortUrl = BASE_URL + shortCode;
        return new ShortenUrlResponse(shortUrl, originalUrl);
    }

    private String generateUniqueShortCode() {
        for (int i = 0; i < MAX_RETRIES; i++) {
            String code = generateRandomCode();
            if (!repository.existsByShortCode(code)) {
                return code;
            }
        }
        throw new RuntimeException("Não foi possível gerar um código único após " + MAX_RETRIES + " tentativas");
    }

    private String generateRandomCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
}
