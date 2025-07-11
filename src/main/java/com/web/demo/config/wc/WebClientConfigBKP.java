package com.web.demo.config.wc;

import com.web.demo.services.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

//@Configuration
public class WebClientConfigBKP {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${fake.rest.jsonPlaceHolder}")
    private String jsonPlaceHolder;

    @Value("${fake.rest.comments}")
    private String commentsUrl;


    //@Autowired
    private CommonWebClientBKP commonWebClient;

    @Bean
    public JsonPlaceHolderClient jsonPlaceHolderClient() {
        LOGGER.info("jsonPlaceHolderClient");
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .createClient(JsonPlaceHolderClient.class,jsonPlaceHolder, headers);
    }

    @Bean
    public TodosRestClient todosRestClient() {
        LOGGER.info("todosRestClient");
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .createClient(TodosRestClient.class,jsonPlaceHolder, headers);
    }

    @Bean
    public UsersRestClient usersRestClient() {
        LOGGER.info("UsersRestClient");
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .createClient(UsersRestClient.class,jsonPlaceHolder, headers);
    }

    @Bean
    public CommentsRestClient commentsRestClient() {
        LOGGER.info("CommentsRestClient");
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .createClient(CommentsRestClient.class,commentsUrl, headers);
    }

    @Bean
    public EmployeeClientService employeeClientService() {
        LOGGER.info("EmployeeClientService");
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .createClient(EmployeeClientService.class,jsonPlaceHolder, headers);
    }

    /*@Bean
    //@LoadBalanced
    public EmployeeClientService employeeClientService() {
        LOGGER.info("employeeClientService");
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("", "");

        return new HospitalWebClient()
                .createClient(EmployeeClientService.class, "", headersMap);
    }*/

   /* @Bean
    public JsonPlaceHolderClient jsonPlaceHolderClient() {
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .httpServiceProxyFactory(jsonPlaceHolder, headers, JsonPlaceHolderClient.class);
    }

    @Bean
    public UsersRestClient usersRestClient() {
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .httpServiceProxyFactory(jsonPlaceHolder, headers, UsersRestClient.class);
    }

    @Bean
    public CommentsRestClient commentsRestClient() {
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .httpServiceProxyFactory(jsonPlaceHolder, headers, CommentsRestClient.class);
    }

    @Bean
    public TodosRestClient todosRestClient() {
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer your-token",
                "Custom-Header", "CustomValue"
        );
        return commonWebClient
                .httpServiceProxyFactory(jsonPlaceHolder, headers, TodosRestClient.class);
    }*/
}
