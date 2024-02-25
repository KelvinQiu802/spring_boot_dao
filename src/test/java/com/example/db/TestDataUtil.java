package com.example.db;

import com.example.db.domain.entity.AuthorEntity;
import com.example.db.domain.entity.BookEntity;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder().
                id(1L).
                name("Kelvin").
                age(80).
                build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder().
                id(2L).
                name("Jack").
                age(44).
                build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder().
                id(3L).
                name("Lima").
                age(24).
                build();
    }


    public static BookEntity createTestBookA(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("1231231")
                .title("Hello World")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookB(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("1231231-49")
                .title("WOW")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookC(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("12312-123-3")
                .title("HAHAHAH")
                .authorEntity(authorEntity)
                .build();
    }

}
