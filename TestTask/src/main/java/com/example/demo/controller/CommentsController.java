package com.example.demo.controller;

import com.example.demo.model.Comments;
import com.example.demo.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping
    public List<Comments> getAllComments(){
        return commentsService.getAllComments();
    }

    @PostMapping
    public Comments addComments(@RequestBody Comments comments){
        return commentsService.addComments(comments);
    }

    @PutMapping("/{id}")
    public void updateNews(@PathVariable String id, @RequestBody Comments comments){
        commentsService.updateComment(id, comments);
    }

    @DeleteMapping("/{id}")
    public String deleteCommentById(@PathVariable String id){
        return commentsService.deleteById(id);
    }
}
