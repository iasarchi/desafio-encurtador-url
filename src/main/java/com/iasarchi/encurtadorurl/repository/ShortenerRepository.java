package com.iasarchi.encurtadorurl.repository;

import com.iasarchi.encurtadorurl.entity.Shortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortenerRepository extends JpaRepository<Shortener,String> {

    @Query("SELECT s FROM shortener s ORDER BY s.clickCount DESC")
    public List<Shortener> findTop10ByOrderByClickCountDesc();
}
