package com.iasarchi.encurtadorurl.repository;

import com.iasarchi.encurtadorurl.entity.Shortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenerRepository extends JpaRepository<Shortener,String> {
}
