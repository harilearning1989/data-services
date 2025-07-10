package com.web.demo.services.client;

import com.web.demo.records.Post;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/posts")
public interface JsonPlaceHolderClient {
    @GetExchange("/{id}")
    Post getPostById(int id);
}

