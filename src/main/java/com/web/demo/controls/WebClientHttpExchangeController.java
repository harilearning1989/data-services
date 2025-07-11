package com.web.demo.controls;

import com.web.demo.records.Comments;
import com.web.demo.records.Posts;
import com.web.demo.records.Todos;
import com.web.demo.records.Users;
import com.web.demo.services.client.CommentsRestClient;
import com.web.demo.services.client.JsonPlaceHolderClient;
import com.web.demo.services.client.TodosRestClient;
import com.web.demo.services.client.UsersRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exchange")
public class WebClientHttpExchangeController {

    private final UsersRestClient usersRestClient;
    private final CommentsRestClient commentsRestClient;
    private final JsonPlaceHolderClient jsonPlaceHolderClient;
    private final TodosRestClient todosRestClient;

    public WebClientHttpExchangeController(UsersRestClient usersRestClient,
                                           CommentsRestClient commentsRestClient,
                                           JsonPlaceHolderClient jsonPlaceHolderClient,
                                           TodosRestClient todosRestClient) {
        this.usersRestClient = usersRestClient;
        this.commentsRestClient = commentsRestClient;
        this.jsonPlaceHolderClient = jsonPlaceHolderClient;
        this.todosRestClient = todosRestClient;
    }

    @GetMapping(value = "/posts")
    public List<Posts> streamAllPosts() {
        return jsonPlaceHolderClient.getAllPosts();
    }

    @GetMapping(value = "/comments")
    public List<Comments> getComments() {
        return commentsRestClient.getAllComments();
    }

    @GetMapping(value = "/users")
    public List<Users> getUsers() {
        return usersRestClient.getAllUsers();
    }

    @GetMapping(value = "/todos")
    public List<Todos> getTodos() {
        return todosRestClient.getAllTodos();
    }


}
