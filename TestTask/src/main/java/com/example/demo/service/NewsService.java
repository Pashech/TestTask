package com.example.demo.service;

import com.example.demo.exception_handling.DuplicateNewsException;
import com.example.demo.exception_handling.NoAnyNewsException;
import com.example.demo.exception_handling.NoSuchNewsException;
import com.example.demo.model.News;
import com.example.demo.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> newsList(){
        List<News> newsList = newsRepository.findAll();
        if(newsList.size() == 0){
            throw new NoAnyNewsException("no news found");
        }
        return newsList;
    }

    public News addNews(News news){
        String title = news.getTitle();
        Optional<News> newsFromDb = newsRepository.findNewsByTitle(title);
        if(newsFromDb.isPresent()){
            throw new DuplicateNewsException("Such news already exists");
        }
        News savedNews = newsRepository.save(news);
        return savedNews;
    }

    public String deleteById(String id){
        Optional<News> news = newsRepository.findNewsById(id);
        if(!news.isPresent()){
            throw new NoSuchNewsException("There is no news with id " + id);
        }
        newsRepository.deleteById(id);
        return "news with ID = " + id + " was deleted";
    }

    public void updateNews(String title, News news){
        Optional<News> newsFromDb = newsRepository.findNewsByTitle(title);
        newsFromDb.get().setTitle(news.getTitle());
        newsFromDb.get().setText(news.getText());
        newsRepository.save(newsFromDb.get());
    }

    public Optional<News> getNewsByTitle(String title){
        Optional<News> news = newsRepository.findNewsByTitle(title);
        if(!news.isPresent()){
            throw new NoSuchNewsException("There is no news with title " + title);
        }
        return news;
    }

    public Optional<News> getNewsById(String id){
        Optional<News> news = newsRepository.findNewsById(id);
        if(!news.isPresent()){
            throw new NoSuchNewsException("There is no news with id " + id);
        }
        return news;
    }
}
