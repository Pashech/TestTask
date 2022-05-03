package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Comments {

    @Id
    private String id;
    private String message;
    private LocalDateTime date;
    private String userName;

    public Comments(){

    }

    public Comments(String id, String message, LocalDateTime date, String userName) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.userName = userName;
    }

    public Comments(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "message='" + message + '\'' +
                ", date=" + date +
                ", userName='" + userName + '\'' +
                '}';
    }
}
