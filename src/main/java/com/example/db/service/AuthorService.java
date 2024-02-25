package com.example.db.service;

import com.example.db.domain.entity.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(long id);

    boolean isExists(long id);

    AuthorEntity partialUpdate(long id, AuthorEntity authorEntity);

    void delete(long id);
}
