package com.web.demo.controls;

import com.web.demo.records.*;
import com.web.demo.services.FakeRestClientService;
import com.web.demo.services.FakeRestTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
public class FakeRestClientController {

    private final FakeRestClientService fakeRestClientService;

    public FakeRestClientController(FakeRestClientService fakeRestClientService) {
        this.fakeRestClientService = fakeRestClientService;
    }

    @GetMapping(value = "/photos")
    public List<Photos> getPhotos() {
        return fakeRestClientService.fetchPhotos();
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return fakeRestClientService.fetchProducts();
    }

    @GetMapping("/carts")
    public CartResponse getCarts() {
        return fakeRestClientService.fetchCarts();
    }

    @GetMapping(value = "/posts")
    public List<Posts> streamAllPosts() {
        return fakeRestClientService.fetchPosts();
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return fakeRestClientService.fetchBooks();
    }

    @GetMapping(value = "/authors")
    public List<Authors> getAuthors() {
        return fakeRestClientService.fetchAuthors();
    }

    @GetMapping("/comments")
    public List<Comments> getComments() {
        return fakeRestClientService.fetchComments();
    }

    @GetMapping("/todos")
    public List<Todos> getTodos() {
        return fakeRestClientService.fetchTodos();
    }

}
