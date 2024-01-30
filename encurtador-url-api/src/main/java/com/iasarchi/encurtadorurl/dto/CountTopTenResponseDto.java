package com.iasarchi.encurtadorurl.dto;

import com.iasarchi.encurtadorurl.entity.Shortener;

public record CountTopTenResponseDto(String url , int count) {
    public CountTopTenResponseDto(Shortener shortener) {
        this(shortener.getOriginalUrl(), shortener.getClickCount());
    }

}
