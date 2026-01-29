package com.gusta.encurtador_url.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ShortenUrlRequest(
    @NotBlank(message = "URL não pode estar vazia")
    @Pattern(
        regexp = "^https?://.+\\..+",
        message = "URL deve ser válida e começar com http:// ou https://"
    )
    String url
) {}
