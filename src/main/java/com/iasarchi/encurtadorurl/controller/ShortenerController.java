package com.iasarchi.encurtadorurl.controller;

import com.iasarchi.encurtadorurl.dto.ShortenerRequestDto;
import com.iasarchi.encurtadorurl.dto.ShortenerResponseDto;
import com.iasarchi.encurtadorurl.entity.Shortener;
import com.iasarchi.encurtadorurl.service.ShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequestMapping("/shortener")
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShortenerController {
    private final ShortenerService shortenerService;

    @PostMapping
    private ResponseEntity<ShortenerResponseDto> save(@RequestBody ShortenerRequestDto shortenerRequestDto) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Shortener shortenerReturned = shortenerService.save(shortenerRequestDto.customAlias(), shortenerRequestDto.url());

        stopWatch.stop();
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String shortUrl = baseUrl + "/" + shortenerReturned.getId();
        ShortenerResponseDto shortenerResponseDto = new ShortenerResponseDto(shortenerReturned, shortUrl, stopWatch.getTotalTimeMillis());

        return ResponseEntity.status(HttpStatus.CREATED).body(shortenerResponseDto);

    }
}
