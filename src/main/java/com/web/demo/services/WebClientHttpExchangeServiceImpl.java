package com.web.demo.services;

import com.web.demo.records.Comment;
import com.web.demo.records.Posts;
import com.web.demo.records.Todo;
import com.web.demo.records.User;
import com.web.demo.services.client.CommentsRestClient;
import com.web.demo.services.client.JsonPlaceHolderClient;
import com.web.demo.services.client.TodosRestClient;
import com.web.demo.services.client.UsersRestClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class WebClientHttpExchangeServiceImpl implements WebClientHttpExchangeService {

    private final UsersRestClient usersRestClient;
    private final CommentsRestClient commentsRestClient;
    private final JsonPlaceHolderClient jsonPlaceHolderClient;
    private final TodosRestClient todosRestClient;

    public WebClientHttpExchangeServiceImpl(UsersRestClient usersRestClient,
                                            CommentsRestClient commentsRestClient,
                                            JsonPlaceHolderClient jsonPlaceHolderClient,
                                            TodosRestClient todosRestClient) {
        this.usersRestClient = usersRestClient;
        this.commentsRestClient = commentsRestClient;
        this.jsonPlaceHolderClient = jsonPlaceHolderClient;
        this.todosRestClient = todosRestClient;
    }

    @Async("webClientExecutor")
    @Override
    public CompletableFuture<List<Posts>> getAllPostsAsync() {
        return CompletableFuture.completedFuture(jsonPlaceHolderClient.getAllPosts());
    }

    @Override
    public List<Posts> getAllPosts() {
        return jsonPlaceHolderClient.getAllPosts();
    }

    @Override
    public Comment getAllComments() {
        return commentsRestClient.getAllComments();
    }

    @Override
    public User getAllUsers() {
        return usersRestClient.getAllUsers();
    }

    @Override
    public Todo getAllTodos() {
        return todosRestClient.getAllTodos();
    }
}
