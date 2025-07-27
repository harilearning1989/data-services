package com.web.demo.controls;

import com.web.demo.records.*;
import com.web.demo.services.WebClientHttpExchangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/exchange")
public class WebClientHttpExchangeController {

    private final WebClientHttpExchangeService webClientHttpExchangeService;

    public WebClientHttpExchangeController(WebClientHttpExchangeService webClientHttpExchangeService) {
        this.webClientHttpExchangeService = webClientHttpExchangeService;
    }

    @GetMapping(value = "/postsAsync")
    public List<Posts> getPhotosAsync() {
        CompletableFuture<List<Posts>> futureProducts = webClientHttpExchangeService.getAllPostsAsync();
        List<Posts> photosList = null;
        try {
            photosList = futureProducts.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return photosList;
    }

    @GetMapping(value = "/posts")
    public List<Posts> streamAllPosts() {
        return webClientHttpExchangeService.getAllPosts();
    }

    @GetMapping(value = "/comments")
    public Comment getComments() {
        return webClientHttpExchangeService.getAllComments();
    }

    @GetMapping(value = "/users")
    public User getUsers() {
        return webClientHttpExchangeService.getAllUsers();
    }

    @GetMapping(value = "/todos")
    public Todo getTodos() {
        return webClientHttpExchangeService.getAllTodos();
    }


}
