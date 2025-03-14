package com.littleurl.repository;

import com.littleurl.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, String> {

    Optional<Url> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);

    long countByExpiresAtAfter(LocalDateTime now);

    long countByExpiresAtBefore(LocalDateTime now);


}
