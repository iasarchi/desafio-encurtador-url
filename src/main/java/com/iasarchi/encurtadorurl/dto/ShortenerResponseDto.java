package com.iasarchi.encurtadorurl.dto;

import com.iasarchi.encurtadorurl.entity.Shortener;

public record ShortenerResponseDto(String alias, String url, long timeTaken) {
    public ShortenerResponseDto(Shortener shortener, long timeTaken){
        this(shortener.getId(), shortener.getUrl(), timeTaken);
    }


}
