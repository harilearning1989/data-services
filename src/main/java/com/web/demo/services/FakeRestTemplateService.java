package com.web.demo.services;

import com.web.demo.records.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FakeRestTemplateService {
    List<Posts> fetchPosts();

    List<Product> fetchProducts();

    CartResponse fetchCarts();

    List<Book> fetchBooks();

    List<Authors> fetchAuthors();

    List<Comments> fetchComments();

    List<Todos> fetchTodos();

    List<Photos> fetchPhotos();

    CompletableFuture<List<Product>> getProductsAsync();

    CompletableFuture<List<Photos>> getPhotosAsync();

    CompletableFuture<CartResponse> getCartsAsync();

    CompletableFuture<List<Posts>> streamAllPostsAsync();

    CompletableFuture<List<Book>> getBooksAsync();

    CompletableFuture<List<Authors>> getAuthorsAsync();

    CompletableFuture<List<Comments>> getCommentsAsync();

    CompletableFuture<List<Todos>> getTodosAsync();
}
