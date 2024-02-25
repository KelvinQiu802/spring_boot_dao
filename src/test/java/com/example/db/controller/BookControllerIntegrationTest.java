package com.example.db.controller;

import com.example.db.TestDataUtil;
import com.example.db.domain.dto.BookDto;
import com.example.db.domain.entity.AuthorEntity;
import com.example.db.domain.entity.BookEntity;
import com.example.db.mapper.Mapper;
import com.example.db.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@Log
public class BookControllerIntegrationTest {

    private BookService bookService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper,
                                         Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    @Test
    public void testCreateBookSuccessfully() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorEntity.setId(null);
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/1231231")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookEntity))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("1231231")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Hello World")
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testGetAllBooks() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("1231231")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("Hello World")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
}
