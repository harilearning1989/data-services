package com.web.demo.services;

import com.web.demo.records.*;
import com.web.demo.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class FakeRestClientServiceImpl implements FakeRestClientService {

    @Value("${fake.rest.jsonPlaceHolder}")
    private String jsonPlaceHolder;
    @Value("${fake.rest.products}")
    private String products;
    @Value("${fake.rest.carts}")
    private String carts;
    @Value("${fake.rest.fakeRestApi}")
    private String fakeRestApi;

    private final RestClient restClient;
    public FakeRestClientServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<Product> fetchProducts() {
        String url = products + "/products";
        List<Product> productList = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return CommonUtils.getLimitedList(productList,10);
    }

    @Override
    public CartResponse fetchCarts() {
        String url = carts + "/carts";
        //return restTemplate.getForObject(url, CartResponse.class);
        return null;
    }

    @Override
    public List<Book> fetchBooks() {
        String url = fakeRestApi + "/api/v1/Books";
        List<Book> bookList = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return CommonUtils.getLimitedList(bookList,10);
    }

    @Override
    public List<Authors> fetchAuthors() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted!");
        }
        String url = fakeRestApi + "/api/v1/Authors";
        List<Authors> authorsList = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        //System.out.println(10/0);
        return CommonUtils.getLimitedList(authorsList,10);
    }

    @Override
    public List<Posts> fetchPosts() {
        String url = jsonPlaceHolder + "/posts";
        /*try {
            TimeUnit.MINUTES.sleep(2); // Sleeps for 2 seconds
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        List<Posts> postsList = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        //return CommonUtils.getLimitedList(postsList,10);
        return postsList;
    }

    @Override
    public List<Comments> fetchComments() {
        String url = jsonPlaceHolder + "/comments";
        List<Comments> commentsList = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return CommonUtils.getLimitedList(commentsList,10);
    }

    @Override
    public List<Todos> fetchTodos() {
        String url = jsonPlaceHolder + "/todos";
        List<Todos> todosList = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return CommonUtils.getLimitedList(todosList,10);
    }

    @Override
    public List<Photos> fetchPhotos() {
        String url = jsonPlaceHolder + "/photos";
        List<Photos> photosList = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return CommonUtils.getLimitedList(photosList,10);
    }
}
