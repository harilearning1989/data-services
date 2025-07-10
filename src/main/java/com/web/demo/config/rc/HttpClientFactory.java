package com.web.demo.config.rc;

//import org.springframework.web.service.invoker.RestClientAdapter;


public class HttpClientFactory {

    /*public static <T> T createClient(String baseUrl, Map<String, String> headers, Class<T> clientClass) {
        RestClient.Builder builder = RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory())
                .baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> {
                    headers.forEach(httpHeaders::add);
                });

        RestClient restClient = builder.build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(RestClientAdapter.forClient(restClient))
                .build();

        return factory.createClient(clientClass);
    }*/
}

