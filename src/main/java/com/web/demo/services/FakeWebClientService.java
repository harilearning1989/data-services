package com.web.demo.services;

import com.web.demo.records.*;
import reactor.core.publisher.Flux;

public interface FakeWebClientService {

    Flux<Posts> fetchPosts();
    Flux<Product> fetchProducts();
    Flux<Cart> fetchCarts();
    Flux<Book> fetchBooks();
    Flux<Authors> fetchAuthors();
    Flux<Comments> fetchComments();
    Flux<Todos> fetchTodos();
    Flux<Photos> fetchPhotos();
}
