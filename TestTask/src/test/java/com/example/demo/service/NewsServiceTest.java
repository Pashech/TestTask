package com.example.demo.service;

import com.example.demo.model.Comments;
import com.example.demo.model.News;
import com.example.demo.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class NewsServiceTest {

    @InjectMocks
    NewsService newsService;

    @Mock
    NewsRepository newsRepository;

    News news1;
    News news2;

    @BeforeEach
    public void preparedData(){
        List<Comments> commentsList = new ArrayList<>();

        Comments comment1 = new Comments("1","some message", LocalDateTime.now(), "Pashech");
        Comments comment2 = new Comments("2", "some message", LocalDateTime.now(), "Yanochka");

        commentsList.add(comment1);
        commentsList.add(comment2);

        news1 = new News("1", LocalDateTime.now(), "news_1", "some text", commentsList);
        news2 = new News("2", LocalDateTime.now(), "news_2", "some text", commentsList);

    }

    @Test
    void newsList() {
        when(newsRepository.findAll()).thenReturn(Stream.of(news1, news2).collect(Collectors.toList()));
        assertEquals(2, newsService.newsList().size());
    }

    @Test
    void getNewsByTitle() {
        when(newsRepository.findNewsByTitle("news_1")).thenReturn(Optional.of(news1));
        assertEquals("news_1", newsService.getNewsByTitle(news1.getTitle()).get().getTitle());
    }

    @Test
    void getNewsById() {
        when(newsRepository.findNewsById("2")).thenReturn(Optional.ofNullable(news2));
        assertEquals("2", newsService.getNewsById(news2.getId()).get().getId());
    }

    @Test
    void addNews() {
        when(newsRepository.save(news1)).thenReturn(news1);
        assertEquals(news1, newsService.addNews(news1));
    }

    @Test
    void deleteById() {
        when(newsRepository.findNewsById("1")).thenReturn(Optional.of(news1));
        newsRepository.deleteById("1");
        verify(newsRepository, times(1)).deleteById(news1.getId());
    }

    @Test
    void updateNews(){
        when(newsRepository.findNewsByTitle("news_1")).thenReturn(Optional.of(news1));
        news1.setTitle("news_100");
        newsRepository.save(news1);

        when(newsRepository.findNewsByTitle("news_100")).thenReturn(Optional.of(news1));
        assertEquals("news_100", newsService.getNewsByTitle(news1.getTitle()).get().getTitle());

    }
}