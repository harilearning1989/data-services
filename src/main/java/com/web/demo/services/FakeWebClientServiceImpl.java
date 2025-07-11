package com.web.demo.services;

import com.web.demo.records.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FakeWebClientServiceImpl implements FakeWebClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeWebClientServiceImpl.class);

    @Value("${fake.rest.jsonPlaceHolder}")
    private String jsonPlaceHolder;
    @Value("${fake.rest.products}")
    private String products;
    @Value("${fake.rest.carts}")
    private String carts;
    @Value("${fake.rest.fakeRestApi}")
    private String fakeRestApi;

    private WebClient createClient(String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    public Flux<Posts> fetchPostsTmp() {
        LOGGER.info("getAllPosts URL::{}", jsonPlaceHolder);
        return createClient(jsonPlaceHolder)
                .get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(Posts.class);
    }

    public Flux<Posts> fetchPosts() {
        LOGGER.info("getAllPosts URL::{}", jsonPlaceHolder);
        return createClient(jsonPlaceHolder)
                .get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(Posts.class)
                .flatMap(this::processPost) // Processing logic
                .onErrorContinue((ex, obj) -> LOGGER.error("Error processing post: {}, Exception: {}", obj, ex.getMessage()));
    }

    private Mono<Posts> processPost(Posts post) {
        if (post.id() == 50) {
            return Mono.error(new RuntimeException("Exception at ID 50"));
        }
        return Mono.just(post);
    }

    public Flux<Product> fetchProducts() {
        LOGGER.info("fetchProducts URL::{}", products);
        return createClient(products)
                .get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(Product.class); // Convert JSON response to Flux<Product>
    }

    public Flux<Cart> fetchCarts() {
        LOGGER.info("fetchCarts URL::{}", carts);
        return createClient(carts)
                .get()
                .uri("/carts")
                .retrieve()
                .bodyToFlux(Cart.class); // Convert JSON response to Flux<Product>
    }

    public Flux<Book> fetchBooks() {
        LOGGER.info("fetchBooks URL::{}", fakeRestApi);
        return createClient(fakeRestApi)
                .get().uri("/api/v1/Books")
                .retrieve()
                .bodyToFlux(Book.class); // Convert JSON response to Flux<Product>
    }

    public Flux<Authors> fetchAuthors() {
        LOGGER.info("fetchAuthors URL::{}", fakeRestApi);
        return createClient(fakeRestApi)
                .get().uri("/api/v1/Authors")
                .retrieve()
                .bodyToFlux(Authors.class);
    }

    public Flux<Comments> fetchComments() {
        LOGGER.info("fetchComments URL::{}", jsonPlaceHolder);
        return createClient(jsonPlaceHolder)
                .get().uri("/comments")
                .retrieve()
                .bodyToFlux(Comments.class);
    }

    public Flux<Todos> fetchTodos() {
        LOGGER.info("fetchTodos URL::{}", jsonPlaceHolder);
        return createClient(jsonPlaceHolder)
                .get().uri("/todos")
                .retrieve()
                .bodyToFlux(Todos.class);
    }

    public Flux<Photos> fetchPhotos() {
        LOGGER.info("fetchPhotos URL::{}", jsonPlaceHolder);
        return createClient(jsonPlaceHolder)
                .get().uri("/photos")
                .retrieve()
                .bodyToFlux(Photos.class);
    }
}
