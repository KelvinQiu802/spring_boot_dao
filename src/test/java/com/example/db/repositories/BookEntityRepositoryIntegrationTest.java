package com.example.db.repositories;

import com.example.db.TestDataUtil;
import com.example.db.domain.entity.AuthorEntity;
import com.example.db.domain.entity.BookEntity;
import com.example.db.repopsitories.BookRepository;
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
public class BookEntityRepositoryIntegrationTest {

    private BookRepository underTest;

    @Autowired
    public BookEntityRepositoryIntegrationTest(BookRepository bookRepository) {
        this.underTest = bookRepository;
    }

    @Test
    public void testBookCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        underTest.save(bookEntity);  // the author will be created automatically
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testMultipleBooksCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntity);
        BookEntity bookEntityB = TestDataUtil.createTestBookB(authorEntity);
        BookEntity bookEntityC = TestDataUtil.createTestBookC(authorEntity);
        underTest.save(bookEntityA);
        underTest.save(bookEntityB);
        underTest.save(bookEntityC);

        Iterable<BookEntity> results = underTest.findAll();
        assertThat(results).hasSize(3);
        assertThat(results).contains(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testBookCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        underTest.save(bookEntity);

        bookEntity.setTitle("UPDATED");
        underTest.save(bookEntity);

        Optional<BookEntity> updated = underTest.findById(bookEntity.getIsbn());
        assertThat(updated).isPresent();
        assertThat(updated.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testBookCanBeDeleted() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        underTest.save(bookEntity);

        underTest.deleteById(bookEntity.getIsbn());
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isEmpty();
    }

}
