package com.iasarchi.encurtadorurl.dto;

public record ShortenerRequestDto(
        String customAlias,
        String url) {
}
