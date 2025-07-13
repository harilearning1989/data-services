package com.web.demo.services;

import com.web.demo.records.*;
import com.web.demo.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class FakeRestTemplateServiceImpl implements FakeRestTemplateService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;

    @Value("${fake.rest.jsonPlaceHolder}")
    private String jsonPlaceHolder;
    @Value("${fake.rest.products}")
    private String products;
    @Value("${fake.rest.carts}")
    private String carts;
    @Value("${fake.rest.fakeRestApi}")
    private String fakeRestApi;

    public FakeRestTemplateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async("restExecutor")
    @Override
    public CompletableFuture<List<Product>> getProductsAsync() {
        LOGGER.info("getProductsAsync Running in thread: {}" , Thread.currentThread().getName());
        String url = products + "/products";
        LOGGER.info("getProductsAsync URL::{}", url);

        List<Product> productList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                }
        ).getBody();

        List<Product> limitedList = CommonUtils.getLimitedList(productList, 10);

        return CompletableFuture.completedFuture(limitedList);
    }

    @Async("restExecutor")
    @Override
    public CompletableFuture<List<Photos>> getPhotosAsync() {
        LOGGER.info("getPhotosAsync Running in thread: {}" , Thread.currentThread().getName());
        String url = jsonPlaceHolder + "/photos";
        LOGGER.info("getPhotosAsync URL::{}", url);
        List<Photos> photosList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Photos>>() {
                }
        ).getBody();
        List<Photos> limitedList = CommonUtils.getLimitedList(photosList,10);
        return CompletableFuture.completedFuture(limitedList);
    }

    @Async("restExecutor")
    @Override
    public CompletableFuture<CartResponse> getCartsAsync() {
        LOGGER.info("getCartsAsync Running in thread: {}" , Thread.currentThread().getName());
        String url = carts + "/carts";
        LOGGER.info("getCartsAsync URL::{}", url);
        CartResponse cartResponse = restTemplate.getForObject(url, CartResponse.class);
        return CompletableFuture.completedFuture(cartResponse);
    }

    @Async("restExecutor")
    @Override
    public CompletableFuture<List<Posts>> streamAllPostsAsync() {
        LOGGER.info("streamAllPostsAsync Running in thread: {}" , Thread.currentThread().getName());
        String url = jsonPlaceHolder + "/posts";
        LOGGER.info("streamAllPostsAsync URL::{}", url);
        /*try {
            TimeUnit.MINUTES.sleep(2); // Sleeps for 2 seconds
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        List<Posts> postsList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Posts>>() {
                }
        ).getBody();
        //return CommonUtils.getLimitedList(postsList,10);
        return CompletableFuture.completedFuture(postsList);
    }

    @Async("restExecutor")
    @Override
    public CompletableFuture<List<Book>> getBooksAsync() {
        LOGGER.info("getBooksAsync Running in thread: {}" , Thread.currentThread().getName());
        String url = fakeRestApi + "/api/v1/Books";
        LOGGER.info("getBooksAsync URL::{}", url);
        List<Book> bookList=  restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {
                }
        ).getBody();
        return CompletableFuture.completedFuture(bookList);
    }

    @Async("restExecutor")
    @Override
    public CompletableFuture<List<Authors>> getAuthorsAsync() {
        LOGGER.info("getAuthorsAsync Running in thread: {}" , Thread.currentThread().getName());
        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread was interrupted!");
        }*/
        String url = fakeRestApi + "/api/v1/Authors";
        LOGGER.info("getAuthorsAsync URL::{}", url);
        List<Authors> authorsList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Authors>>() {
                }
        ).getBody();
        //System.out.println(10/0);
        List<Authors> limitedList= CommonUtils.getLimitedList(authorsList,10);
        return CompletableFuture.completedFuture(limitedList);
    }

    @Async("restExecutor")
    @Override
    public CompletableFuture<List<Comments>> getCommentsAsync() {
        LOGGER.info("getCommentsAsync Running in thread: {}" , Thread.currentThread().getName());
        String url = jsonPlaceHolder + "/comments";
        LOGGER.info("getCommentsAsync URL::{}", url);
        List<Comments> commentsList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Comments>>() {
                }
        ).getBody();
        List<Comments> limitedList= CommonUtils.getLimitedList(commentsList,10);
        return CompletableFuture.completedFuture(limitedList);
    }

    @Async("restExecutor")
    @Override
    public CompletableFuture<List<Todos>> getTodosAsync() {
        LOGGER.info("getTodosAsync Running in thread: {}" , Thread.currentThread().getName());
        String url = jsonPlaceHolder + "/todos";
        LOGGER.info("getTodosAsync URL::{}", url);
        List<Todos> todosList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Todos>>() {
                }
        ).getBody();
        List<Todos> limitedList= CommonUtils.getLimitedList(todosList,10);
        return CompletableFuture.completedFuture(limitedList);
    }

    @Override
    public List<Product> fetchProducts() {
        String url = products + "/products";
        LOGGER.info("fetchProducts URL::{}", url);
        List<Product> productList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                }
        ).getBody();
        return CommonUtils.getLimitedList(productList,10);
    }

    @Override
    public CartResponse fetchCarts() {
        String url = carts + "/carts";
        LOGGER.info("fetchCarts URL::{}", url);
        return restTemplate.getForObject(url, CartResponse.class);
    }

    @Override
    public List<Book> fetchBooks() {
        String url = fakeRestApi + "/api/v1/Books";
        LOGGER.info("fetchBooks URL::{}", url);
        List<Book> bookList=  restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {
                }
        ).getBody();
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
        LOGGER.info("fetchAuthors URL::{}", url);
        List<Authors> authorsList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Authors>>() {
                }
        ).getBody();
        //System.out.println(10/0);
        return CommonUtils.getLimitedList(authorsList,10);
    }

    @Override
    public List<Posts> fetchPosts() {
        String url = jsonPlaceHolder + "/posts";
        LOGGER.info("fetchPosts URL::{}", url);
        /*try {
            TimeUnit.MINUTES.sleep(2); // Sleeps for 2 seconds
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        List<Posts> postsList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Posts>>() {
                }
        ).getBody();
        //return CommonUtils.getLimitedList(postsList,10);
        return postsList;
    }

    @Override
    public List<Comments> fetchComments() {
        String url = jsonPlaceHolder + "/comments";
        LOGGER.info("fetchComments URL::{}", url);
        List<Comments> commentsList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Comments>>() {
                }
        ).getBody();
        return CommonUtils.getLimitedList(commentsList,10);
    }

    @Override
    public List<Todos> fetchTodos() {
        String url = jsonPlaceHolder + "/todos";
        LOGGER.info("fetchTodos URL::{}", url);
        List<Todos> todosList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Todos>>() {
                }
        ).getBody();
        return CommonUtils.getLimitedList(todosList,10);
    }

    @Override
    public List<Photos> fetchPhotos() {
        String url = jsonPlaceHolder + "/photos";
        LOGGER.info("fetchPhotos URL::{}", url);
        List<Photos> photosList = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Photos>>() {
                }
        ).getBody();
        return CommonUtils.getLimitedList(photosList,10);
    }


}
