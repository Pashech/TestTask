package com.example.demo.service;

import com.example.demo.exception_handling.DuplicateCommentException;
import com.example.demo.exception_handling.NoAnyCommentsException;
import com.example.demo.exception_handling.NoSuchCommentsException;
import com.example.demo.model.Comments;
import com.example.demo.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    @Autowired
    private final CommentsRepository commentsRepository;

    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public List<Comments> getAllComments(){
        List<Comments> commentsList = commentsRepository.findAll();
        if(commentsList.size() == 0){
            throw new NoAnyCommentsException("No comments found");
        }
        return commentsList;
    }

    public Comments addComments(Comments comments){
        String title = comments.getId();
        Optional<Comments> newsFromDb = commentsRepository.findCommentsById(title);
        if(newsFromDb.isPresent()){
            throw new DuplicateCommentException("Such comment already exists");
        }
        Comments savedComments = commentsRepository.save(comments);
        return savedComments;
    }

    public void updateComment(String id, Comments comments){
        Optional<Comments> commentsFromDb = commentsRepository.findCommentsById(id);
        if(!commentsFromDb.isPresent()){
            throw new NoSuchCommentsException("There is no comment with id " + id);
        }
        commentsFromDb.get().setMessage(comments.getMessage());
        commentsRepository.save(commentsFromDb.get());
    }

    public String deleteById(String id){
        Optional<Comments> comments = commentsRepository.findCommentsById(id);
        if(!comments.isPresent()){
            throw new NoSuchCommentsException("There is no comment with id " + id);
        }
        commentsRepository.deleteById(id);
        return "comment with ID = " + id + " was deleted";
    }

    public Optional<Comments> getCommentsById(String id){
        Optional<Comments> comment = commentsRepository.findCommentsById(id);
        if(!comment.isPresent()){
            throw new NoSuchCommentsException("There is no comment with id " + id);
        }
        return comment;
    }
}
