package com.web.demo.services.client;

import com.web.demo.records.User;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/users")
public interface UsersRestClient {

    @GetExchange
    User getAllUsers();
}
