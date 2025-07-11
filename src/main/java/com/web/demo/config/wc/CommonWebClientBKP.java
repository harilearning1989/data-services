package com.web.demo.config.wc;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Map;

//@Component
public class CommonWebClientBKP {

    public <S> S createClient(Class<S> serviceType, String url, Map<String, String> headersMap) {
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(createWebClient(url, headersMap)))
                .build()
                .createClient(serviceType);
    }

    private WebClient createWebClient(String url, Map<String,String> headers){
        return WebClient.builder()
                .baseUrl(url)
                /*.filters(exchangeFilterFunctions -> {
                    exchangeFilterFunctions.add(new AuditClientFilter());
                })*/
                .defaultHeaders(header -> headers.forEach(header::add))
                .build();
    }

    /*public <T> T createClient(String baseUrl, Map<String, String> headers, Class<T> clientInterface) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> headers.forEach(httpHeaders::add))
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build();

        return factory.createClient(clientInterface);
    }

    public <T> T httpServiceProxyFactory(String baseUrl, Map<String, String> headers, Class<T> clientClass) {
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(createWebClient(baseUrl, headers, clientClass)))
                .build()
                .createClient(clientClass);
    }

    public <T> T createWebClient(String baseUrl,Map<String, String> headers,Class<T> clientInterface) {
        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .build();

        return factory.createClient(clientInterface);
    }*/
}
