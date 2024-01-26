package com.iasarchi.encurtadorurl.dto;

import com.iasarchi.encurtadorurl.entity.Shortener;

public record ShortenerResponseDto(String alias, String url) {
    public ShortenerResponseDto(Shortener shortener){
        this(shortener.getId(), shortener.getUrl());
    }
}
