package com.iasarchi.encurtadorurl.entity;

import com.iasarchi.encurtadorurl.dto.ShortenerRequestDto;
import com.iasarchi.encurtadorurl.dto.ShortenerResponseDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "shortener")
@Table(name = "shortener")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Shortener {
    @Id
    String id;
    String url;
}

