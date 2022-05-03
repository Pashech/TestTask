package com.example.demo.service;

import com.example.demo.model.Comments;
import com.example.demo.model.News;
import com.example.demo.repository.CommentsRepository;
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
class CommentsServiceTest {

    @InjectMocks
    CommentsService commentsService;

    @Mock
    CommentsRepository commentsRepository;

    Comments comment1;
    Comments comment2;

    @BeforeEach
    public void preparedData(){

        comment1 = new Comments("1","some message", LocalDateTime.now(), "Pashech");
        comment2 = new Comments("2", "some message", LocalDateTime.now(), "Yanochka");
    }

    @Test
    void getAllComments() {
        when(commentsRepository.findAll()).thenReturn(Stream.of(comment1, comment2).collect(Collectors.toList()));
        assertEquals(2, commentsService.getAllComments().size());
    }

    @Test
    void getCommentById() {
        when(commentsRepository.findCommentsById("2")).thenReturn(Optional.of(comment2));
        assertEquals("2", commentsService.getCommentsById(comment2.getId()).get().getId());
    }

    @Test
    void addComments() {
        when(commentsRepository.save(comment1)).thenReturn(comment1);
        assertEquals(comment1, commentsService.addComments(comment1));
    }

    @Test
    void updateComments() {
        when(commentsRepository.findCommentsById("1")).thenReturn(Optional.of(comment1));
        comment1.setId("3");
        commentsRepository.save(comment1);

        when(commentsRepository.findCommentsById("3")).thenReturn(Optional.of(comment1));
        assertEquals("3", commentsService.getCommentsById(comment1.getId()).get().getId());
    }

    @Test
    void deleteById() {
        commentsRepository.deleteById("1");
        verify(commentsRepository, times(1)).deleteById(comment1.getId());
    }
}