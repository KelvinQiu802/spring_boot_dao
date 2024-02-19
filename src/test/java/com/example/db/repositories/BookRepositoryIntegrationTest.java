package com.example.db.repositories;

import com.example.db.TestDataUtil;
import com.example.db.domain.Author;
import com.example.db.domain.Book;
import com.example.db.repopsitories.AuthorRepository;
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
public class BookRepositoryIntegrationTest {

    private AuthorRepository authorRepository;
    private BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTest(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.underTest = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Test
    public void testBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        authorRepository.save(author);
        Book book = TestDataUtil.createTestBookA(author);
        book.setAuthor(author);
        underTest.save(book);
        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

//    @Test
//    public void testMultipleBooksCanBeCreatedAndRecalled() {
//        Author author = TestDataUtil.createTestAuthorA();
//        authorDao.create(author);
//        Book bookA = TestDataUtil.createTestBookA();
//        Book bookB = TestDataUtil.createTestBookB();
//        Book bookC = TestDataUtil.createTestBookC();
//        bookA.setAuthorId(author.getId());
//        bookB.setAuthorId(author.getId());
//        bookC.setAuthorId(author.getId());
//        underTest.create(bookA);
//        underTest.create(bookB);
//        underTest.create(bookC);
//
//        List<Book> results = underTest.find();
//        assertThat(results).hasSize(3);
//        assertThat(results).contains(bookA, bookB, bookC);
//    }
//
//    @Test
//    public void testBookCanBeUpdated() {
//        Author author = TestDataUtil.createTestAuthorA();
//        authorDao.create(author);
//        Book book = TestDataUtil.createTestBookA();
//        book.setAuthorId(author.getId());
//        underTest.create(book);
//
//        book.setTitle("UPDATED");
//        underTest.update(book.getIsbn(), book);
//
//        Optional<Book> updated = underTest.findOne(book.getIsbn());
//        assertThat(updated).isPresent();
//        assertThat(updated.get()).isEqualTo(book);
//    }
//
//    @Test
//    public void testBookCanBeDeleted() {
//        Author author = TestDataUtil.createTestAuthorA();
//        authorDao.create(author);
//        Book book = TestDataUtil.createTestBookA();
//        underTest.create(book);
//
//        underTest.delete(book.getIsbn());
//        Optional<Book> result = underTest.findOne(book.getIsbn());
//        assertThat(result).isEmpty();
//    }

}
