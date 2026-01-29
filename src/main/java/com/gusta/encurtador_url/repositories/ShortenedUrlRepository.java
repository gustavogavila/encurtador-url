package com.gusta.encurtador_url.repositories;

import com.gusta.encurtador_url.entities.ShortenedUrl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenedUrlRepository extends CrudRepository<ShortenedUrl, Long> {
    boolean existsByShortCode(String shortCode);
}
