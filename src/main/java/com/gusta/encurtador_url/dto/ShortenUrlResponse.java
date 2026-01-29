package com.gusta.encurtador_url.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortenUrlResponse(
    @JsonProperty("short_url")
    String shortUrl,

    @JsonProperty("original_url")
    String originalUrl
) {}
