package com.web.demo.config.rt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
/*
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(this.getClientHttpRequestFactory());
    }

    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
        // Configure connection pool manager
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(100);
        // Removed deprecated setValidateAfterInactivity

        // Set default socket configuration
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(Timeout.ofSeconds(10)) // Socket read timeout
                .build();
        connectionManager.setDefaultSocketConfig(socketConfig);

        // Build Apache HttpClient 5.x
        HttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .evictExpiredConnections()
                .evictIdleConnections(TimeValue.ofMinutes(5)) // Remove idle connections
                .build();

        // Create request factory using the custom HttpClient
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(Duration.ofSeconds(5));              // Max time to establish connection
        factory.setConnectionRequestTimeout(Duration.ofSeconds(5));    // Max time to wait for connection from pool
        factory.setReadTimeout(Duration.ofSeconds(5));                 // Max time to wait for a response

        return factory;
    }*/

}

