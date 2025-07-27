package com.web.demo.services;

import com.web.demo.records.Comment;
import com.web.demo.records.Posts;
import com.web.demo.records.Todo;
import com.web.demo.records.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface WebClientHttpExchangeService {
    List<Posts> getAllPosts();
    Comment getAllComments();
    User getAllUsers();
    Todo getAllTodos();

    CompletableFuture<List<Posts>> getAllPostsAsync();
}
