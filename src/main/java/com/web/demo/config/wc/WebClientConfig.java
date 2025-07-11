package com.web.demo.config.wc;

import com.web.demo.services.client.CommentsRestClient;
import com.web.demo.services.client.JsonPlaceHolderClient;
import com.web.demo.services.client.TodosRestClient;
import com.web.demo.services.client.UsersRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class WebClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientConfig.class);

    @Autowired
    private CommonWebClient commonWebClient;

    @Value("${fake.rest.jsonPlaceHolder}")
    private String jsonPlaceHolder;
    @Value("${fake.rest.users}")
    private String usersUrl;

    @Bean
    public JsonPlaceHolderClient jsonPlaceHolderClient() {
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .createClient(jsonPlaceHolder, headers, JsonPlaceHolderClient.class);
    }

    @Bean
    public UsersRestClient usersRestClient() {
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .createClient(usersUrl, headers, UsersRestClient.class);
    }

    @Bean
    public CommentsRestClient commentsRestClient() {
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .createClient(usersUrl, headers, CommentsRestClient.class);
    }

    @Bean
    public TodosRestClient todosRestClient() {
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .createClient(usersUrl, headers, TodosRestClient.class);
    }
}
