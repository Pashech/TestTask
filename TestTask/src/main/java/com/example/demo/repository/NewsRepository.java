package com.example.demo.repository;

import com.example.demo.model.News;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends MongoRepository<News, String> {

        Optional<News> findNewsByTitle(String title);
        Optional<News> findNewsById(String id);

        void deleteById(String id);
}
