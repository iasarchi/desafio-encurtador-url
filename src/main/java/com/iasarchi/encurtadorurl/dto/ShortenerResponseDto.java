package com.iasarchi.encurtadorurl.dto;

import com.iasarchi.encurtadorurl.entity.Shortener;

public record ShortenerResponseDto(String alias, String url,String originalUrl, String timeTaken) {
    public ShortenerResponseDto(Shortener shortener, String shortUrl, long timeTaken){
        this(shortener.getId(), shortUrl, shortener.getOriginalUrl(), timeTaken + "ms");
    }


}
