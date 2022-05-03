package com.example.demo.controller;

import com.example.demo.exception_handling.NoAnyCommentsException;
import com.example.demo.exception_handling.NoSuchCommentsException;
import com.example.demo.exception_handling.NoSuchNewsException;
import com.example.demo.model.Comments;
import com.example.demo.model.News;
import com.example.demo.repository.CommentsRepository;
import com.example.demo.repository.NewsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class CommentsControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void resetDb() {
        commentsRepository.deleteAll();
    }

    private Comments createComment(String id, String userName){
        Comments comment = new Comments(id, userName);
        return commentsRepository.save(comment);
    }

    @Test
    void getAllComments() throws Exception{
        Comments comment1 = createComment("1","Pashech");
        Comments comment2 = createComment("2","Yanochka");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(comment1, comment2))));
    }

    @Test
    void getAllCommentsException() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/comments"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(NoAnyCommentsException.class));
    }

    @Test
    void addComments() throws Exception{
        Comments comment = new Comments("1", "some text", LocalDateTime.now(), "Pashech");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/comments")
                        .content(objectMapper.writeValueAsString(comment))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Pashech"));
    }


    @Test
    void deleteCommentById() throws Exception{
        Comments comment = createComment("1", "Pashech");

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/comments/{id}", comment.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCommentsByIdException() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/comments/{id}", "2"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(NoSuchCommentsException.class));
    }
}