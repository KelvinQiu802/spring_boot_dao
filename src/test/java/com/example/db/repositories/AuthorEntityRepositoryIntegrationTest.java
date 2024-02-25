package com.example.db.repositories;

import com.example.db.TestDataUtil;
import com.example.db.domain.entity.AuthorEntity;
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
public class AuthorEntityRepositoryIntegrationTest {

    private AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTest(AuthorRepository authorRepository) {
        this.underTest = authorRepository;
    }

    @Test
    public void testAuthorCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testMultipleAuthorsCanBeCreatedAndRecalled() {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> results = underTest.findAll();
        assertThat(results).hasSize(3);
        assertThat(results).contains(authorEntityA, authorEntityB, authorEntityC);
    }

    @Test
    public void testAuthorCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        underTest.save(authorEntity);

        authorEntity.setName("UPDATED");
        underTest.save(authorEntity);  // re-saving will handle the update
        Optional<AuthorEntity> updated = underTest.findById(authorEntity.getId());

        assertThat(updated).isPresent();
        assertThat(updated.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testAuthorCanBeDeleted() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        underTest.save(authorEntity);

        underTest.deleteById(authorEntity.getId());
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetAuthorWithAgeLessThan() {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);

        // JPA know what this method means and create the impl automatically
        Iterable<AuthorEntity> results = underTest.ageLessThan(50);
        assertThat(results).contains(authorEntityB, authorEntityC);
    }

}
