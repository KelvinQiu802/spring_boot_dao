package com.example.db.dao.impl;

import com.example.db.TestDataUtil;
import com.example.db.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testCreateBookGenerateCorrectSql() {
        Book book = TestDataUtil.createTestBookA();
        underTest.create(book);

        verify(jdbcTemplate).update("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)",
                "1231231", "Hello World", 1L);
    }

    @Test
    public void testFindOneGenerateCorrectSql() {
        underTest.findOne("1231231");
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("1231231")
        );
    }

    @Test
    public void testFindGenerateCorrectSql() {
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

    @Test
    public void testUpdateGenerateCorrectSql() {
        Book book = TestDataUtil.createTestBookA();
        underTest.update(book.getIsbn(), book);
        verify(jdbcTemplate).update(
                "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthorId(),
                book.getIsbn()
        );
    }

    @Test
    public void testDeleteBookGenerateCorrectSql() {
        Book book = TestDataUtil.createTestBookA();
        underTest.delete(book.getIsbn());

        verify(jdbcTemplate).update(
                "DELETE FROM books WHERE isbn = ?",
                book.getIsbn()
        );
    }

}
