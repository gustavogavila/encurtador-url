package com.gusta.encurtador_url.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("shortened_urls")
public record ShortenedUrl(
    @Id
    Long id,
    String shortCode,
    String originalUrl,
    LocalDateTime createdAt
) {
    public ShortenedUrl(String shortCode, String originalUrl) {
        this(null, shortCode, originalUrl, LocalDateTime.now());
    }
}
