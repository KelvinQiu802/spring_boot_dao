package com.example.db;

import com.example.db.domain.Author;
import com.example.db.domain.Book;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static Author createTestAuthorA() {
        return Author.builder().
                id(1L).
                name("Kelvin").
                age(20).
                build();
    }

    public static Author createTestAuthorB() {
        return Author.builder().
                id(2L).
                name("Jack").
                age(21).
                build();
    }

    public static Author createTestAuthorC() {
        return Author.builder().
                id(3L).
                name("Lima").
                age(19).
                build();
    }


    public static Book createTestBookA() {
        return Book.builder()
                .isbn("1231231")
                .title("Hello World")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .isbn("1231231-49")
                .title("WOW")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookC() {
        return Book.builder()
                .isbn("12312-123-3")
                .title("HAHAHAH")
                .authorId(1L)
                .build();
    }

}