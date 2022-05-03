package com.example.demo.controller;

import com.example.demo.exception_handling.NoAnyNewsException;
import com.example.demo.exception_handling.NoSuchNewsException;
import com.example.demo.model.Comments;
import com.example.demo.model.News;
import com.example.demo.repository.NewsRepository;
import com.example.demo.service.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class NewsControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void resetDb() {
        newsRepository.deleteAll();
    }

    private News createNews(String id, String title){
        News news = new News(id, title);
        return newsRepository.save(news);
    }

    @Test
    void getAllNews() throws Exception {
        News news1 = createNews("1","news_1");
        News news2 = createNews("2","news_2");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/news"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(news1, news2))));
    }

    @Test
    void getAllNewsException() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/news"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(NoAnyNewsException.class));
    }

    @Test
    void getNewsByTitle() throws Exception{
        String title = createNews("1", "news_1").getTitle();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/news/{title}", title))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("news_1"));
    }


    @Test
    void getNewsByTitleException() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/news/1"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(NoSuchNewsException.class));
    }

    @Test
    void addNews() throws Exception{
        News news = new News("news_1");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/news")
                        .content(objectMapper.writeValueAsString(news))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("news_1"));
    }
    
    @Test
    void deleteNewsById() throws Exception{
        News news = createNews("1", "news_1");

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/news/{id}", news.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNewsByIdException() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/news/{id}", "2"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(NoSuchNewsException.class));
    }
}