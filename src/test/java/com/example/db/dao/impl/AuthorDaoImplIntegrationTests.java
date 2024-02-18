package com.example.db.dao.impl;

import com.example.db.TestDataUtil;
import com.example.db.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTests {

    private AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl authorDao) {
        this.underTest = authorDao;
    }

    @Test
    public void testAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.create(authorA);
        underTest.create(authorB);
        underTest.create(authorC);

        List<Author> results = underTest.find();
        assertThat(results).hasSize(3);
        assertThat(results).contains(authorA, authorB, authorC);
    }

    @Test
    public void testAuthorCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.create(author);

        author.setName("UPDATED");
        underTest.update(author.getId(), author);
        Optional<Author> updated = underTest.findOne(author.getId());

        assertThat(updated).isPresent();
        assertThat(updated.get()).isEqualTo(author);
    }

    @Test
    public void testAuthorCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.create(author);

        underTest.delete(author.getId());
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isEmpty();
    }

}
