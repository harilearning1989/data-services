package com.web.demo.controls;

import com.web.demo.records.*;
import com.web.demo.services.FakeWebClientService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stream")
public class FakeWebClientController {

    private final FakeWebClientService fakeWebClientService;

    public FakeWebClientController(FakeWebClientService fakeApiService) {
        this.fakeWebClientService = fakeApiService;
    }

    @GetMapping(value = "/posts", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Posts> streamAllPosts() {
        return fakeWebClientService.fetchPosts();
    }

    @GetMapping(value = "/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProducts() {
        return fakeWebClientService.fetchProducts();
    }

    @GetMapping(value = "/carts", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Cart> getCarts() {
        return fakeWebClientService.fetchCarts();
    }

    @GetMapping(value = "/books", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Book> getBooks() {
        return fakeWebClientService.fetchBooks();
    }

    @GetMapping(value = "/authors", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Authors> getAuthors() {
        return fakeWebClientService.fetchAuthors();
    }

    @GetMapping(value = "/comments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Comments> getComments() {
        return fakeWebClientService.fetchComments();
    }

    @GetMapping(value = "/todos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Todos> getTodos() {
        return fakeWebClientService.fetchTodos();
    }

    @GetMapping(value = "/photos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Photos> getPhotos() {
        return fakeWebClientService.fetchPhotos();
    }
}

