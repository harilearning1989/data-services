package com.web.demo.services.client;

import com.web.demo.records.Todo;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/todos")
public interface TodosRestClient {

    @GetExchange
    Todo getAllTodos();
}
