package com.web.demo.controls;

import com.web.demo.records.*;
import com.web.demo.services.FakeRestTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/fake")
public class FakeRestTemplateController {

    private final FakeRestTemplateService fakeRestTemplateService;

    public FakeRestTemplateController(FakeRestTemplateService fakeRestTemplateService) {
        this.fakeRestTemplateService = fakeRestTemplateService;
    }

    @GetMapping(value = "/photosAsync")
    public List<Photos> getPhotosAsync() {
        CompletableFuture<List<Photos>> futureProducts = fakeRestTemplateService.getPhotosAsync();
        List<Photos> photosList = null;
        try {
            photosList = futureProducts.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return photosList;
    }

    @GetMapping("/productsAsync")
    public List<Product> getProductsAsync() {
        CompletableFuture<List<Product>> futureProducts = fakeRestTemplateService.getProductsAsync();
        List<Product> products = null;
        try {
            products = futureProducts.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @GetMapping("/cartsAsync")
    public CartResponse getCartsAsync() {
        CompletableFuture<CartResponse> futureProducts = fakeRestTemplateService.getCartsAsync();
        CartResponse cartResponse = null;
        try {
            cartResponse = futureProducts.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return cartResponse;
    }

    @GetMapping(value = "/postsAsync")
    public List<Posts> streamAllPostsAsync() {
        CompletableFuture<List<Posts>> futureProducts = fakeRestTemplateService.streamAllPostsAsync();
        List<Posts> cartResponse = null;
        try {
            cartResponse = futureProducts.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return cartResponse;
    }

    @GetMapping("/booksAsync")
    public List<Book> getBooksAsync() {
        CompletableFuture<List<Book>> futureProducts = fakeRestTemplateService.getBooksAsync();
        List<Book> cartResponse = null;
        try {
            cartResponse = futureProducts.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return cartResponse;
    }

    @GetMapping(value = "/authorsAsync")
    public List<Authors> getAuthorsAsync() {
        CompletableFuture<List<Authors>> futureProducts = fakeRestTemplateService.getAuthorsAsync();
        List<Authors> cartResponse = null;
        try {
            cartResponse = futureProducts.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return cartResponse;
    }

    @GetMapping("/commentsAsync")
    public List<Comments> getCommentsAsync() {
        CompletableFuture<List<Comments>> futureProducts = fakeRestTemplateService.getCommentsAsync();
        List<Comments> cartResponse = null;
        try {
            cartResponse = futureProducts.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return cartResponse;
    }

    @GetMapping("/todosAsync")
    public List<Todos> getTodosAsync() {
        CompletableFuture<List<Todos>> futureProducts = fakeRestTemplateService.getTodosAsync();
        List<Todos> cartResponse = null;
        try {
            cartResponse = futureProducts.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return cartResponse;
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
