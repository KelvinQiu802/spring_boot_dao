package com.example.db.repopsitories;

import com.example.db.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Iterable<Author> ageLessThan(int age);

    // HQL
    @Query("SELECT a from Author where a.age > ?1")
    Iterable<Author> findAuthorAgeGraterThan(int age);
}
