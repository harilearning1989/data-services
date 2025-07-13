package com.web.demo.services.client;

import com.web.demo.records.Comment;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/comments")
public interface CommentsRestClient {

    @GetExchange
    Comment getAllComments();
}
