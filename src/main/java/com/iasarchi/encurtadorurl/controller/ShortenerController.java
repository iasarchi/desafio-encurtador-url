package com.iasarchi.encurtadorurl.controller;

import com.iasarchi.encurtadorurl.dto.ShortenerRequestDto;
import com.iasarchi.encurtadorurl.dto.ShortenerResponseDto;
import com.iasarchi.encurtadorurl.entity.Shortener;
import com.iasarchi.encurtadorurl.service.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/shortener")
@RestController
public class ShortenerController {
    @Autowired
    ShortenerService shortenerService ;
    @PostMapping
    private ResponseEntity<ShortenerResponseDto> save(@RequestBody ShortenerRequestDto shortenerRequestDto) throws Exception{
        Shortener shortenerReturned = shortenerService.save(shortenerRequestDto.customAlias(), shortenerRequestDto.url());

        ShortenerResponseDto shortenerResponseDto = new ShortenerResponseDto(shortenerReturned);

        return ResponseEntity.status(HttpStatus.CREATED).body(shortenerResponseDto);

    }
}
