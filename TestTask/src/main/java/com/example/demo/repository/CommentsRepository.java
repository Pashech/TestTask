package com.example.demo.repository;

import com.example.demo.model.Comments;
import com.example.demo.model.News;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentsRepository extends MongoRepository<Comments, String> {

    Optional<Comments> findCommentsById(String id);

    void deleteById(String id);
}
