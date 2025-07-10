package com.web.demo.controls;

import com.web.demo.records.*;
import com.web.demo.services.FakeRestTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fake")
public class FakeRestTemplateController {

    private final FakeRestTemplateService fakeRestTemplateService;

    public FakeRestTemplateController(FakeRestTemplateService fakeRestTemplateService) {
        this.fakeRestTemplateService = fakeRestTemplateService;
    }

    @GetMapping(value = "/photos")
    public List<Photos> getPhotos() {
        return fakeRestTemplateService.fetchPhotos();
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return fakeRestTemplateService.fetchProducts();
    }

    @GetMapping("/carts")
    public CartResponse getCarts() {
        return fakeRestTemplateService.fetchCarts();
    }

    @GetMapping(value = "/posts")
    public List<Posts> streamAllPosts() {
        return fakeRestTemplateService.fetchPosts();
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return fakeRestTemplateService.fetchBooks();
    }

    @GetMapping(value = "/authors")
    public List<Authors> getAuthors() {
        return fakeRestTemplateService.fetchAuthors();
    }

    @GetMapping("/comments")
    public List<Comments> getComments() {
        return fakeRestTemplateService.fetchComments();
    }

    @GetMapping("/todos")
    public List<Todos> getTodos() {
        return fakeRestTemplateService.fetchTodos();
    }


}
