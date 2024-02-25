package com.example.db.repositories;

import com.example.db.TestDataUtil;
import com.example.db.domain.Author;
import com.example.db.repopsitories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTest {

    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTest(AuthorRepository authorRepository) {
        this.underTest = authorRepository;
    }

    @Test
    public void testAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        Iterable<Author> results = underTest.findAll();
        assertThat(results).hasSize(3);
        assertThat(results).contains(authorA, authorB, authorC);
    }

    @Test
    public void testAuthorCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);

        author.setName("UPDATED");
        underTest.save(author);  // re-saving will handle the update
        Optional<Author> updated = underTest.findById(author.getId());

        assertThat(updated).isPresent();
        assertThat(updated.get()).isEqualTo(author);
    }

    @Test
    public void testAuthorCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.save(author);

        underTest.deleteById(author.getId());
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetAuthorWithAgeLessThan() {
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        // JPA know what this method means and create the impl automatically
        Iterable<Author> results = underTest.ageLessThan(50);
        assertThat(results).contains(authorB, authorC);
    }

    @Test
    public void testGetAuthorWithAgeGraterThan() {
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorA);
        underTest.save(authorB);
        underTest.save(authorC);

        // JPA can figure out the impl by this method name
        Iterable<Author> result = underTest.findAuthorAgeGraterThan(50);
        assertThat(result).containsExactly(authorA);
    }

}
