package com.web.demo.services;

import com.web.demo.records.*;

import java.util.List;

public interface FakeRestClientService {

    List<Posts> fetchPosts();

    List<Product> fetchProducts();

    CartResponse fetchCarts();

    List<Book> fetchBooks();

    List<Authors> fetchAuthors();

    List<Comments> fetchComments();

    List<Todos> fetchTodos();

    List<Photos> fetchPhotos();

}
