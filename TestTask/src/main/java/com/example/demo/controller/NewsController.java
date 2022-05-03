package com.example.demo.controller;

import com.example.demo.model.News;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/news")
public class NewsController {

    @Autowired
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public List<News> getAllNews(){
       return newsService.newsList();
    }

    @GetMapping("/{title}")
    public Optional<News> getNewsByTitle(@PathVariable String title){
       return newsService.getNewsByTitle(title);
    }

    @PostMapping
    public News addNews(@RequestBody News news){
        return newsService.addNews(news);
    }

    @PutMapping("/{title}")
    public void updateNews(@PathVariable String title, @RequestBody News news){
        newsService.updateNews(title, news);
    }

    @DeleteMapping("/{id}")
    public String deleteNewsById(@PathVariable String id){
        return newsService.deleteById(id);
    }
}
