package com.example.db.controller;

import com.example.db.domain.dto.BookDto;
import com.example.db.domain.entity.BookEntity;
import com.example.db.mapper.Mapper;
import com.example.db.service.BookService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log
public class BookController {

    private final BookService bookService;

    private final Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn,
                                              @RequestBody final BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        log.info(bookEntity.toString());
        BookEntity createdBook = bookService.createBook(isbn, bookEntity);
        BookDto result = bookMapper.mapTo(createdBook);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    // spring will auto-inject the Pageable
    public Page<BookDto> getAllBooks(Pageable pageable) {
        Page<BookEntity> bookEntities = bookService.findAll(pageable);
        return bookEntities.map(bookMapper::mapTo);
    }

}
