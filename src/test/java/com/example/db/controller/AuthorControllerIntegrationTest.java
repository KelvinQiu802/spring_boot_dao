package com.example.db.controller;

import com.example.db.TestDataUtil;
import com.example.db.domain.entity.AuthorEntity;
import com.example.db.service.AuthorService;
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
public class AuthorControllerIntegrationTest {

    private AuthorService authorService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
    }


    @Test
    public void testCreateAuthorSuccessfully() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorEntity.setId(null);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorEntity))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Kelvin")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value("80")
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testGetAllAuthorsSuccessfully() throws Exception {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        authorService.save(authorEntityA);
        authorService.save(authorEntityB);
        authorService.save(authorEntityC);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]").value(authorEntityA)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]").value(authorEntityB)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2]").value(authorEntityC)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testAuthorCanBeDeleted() throws Exception {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + authorEntityA.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + authorEntityA.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

}
