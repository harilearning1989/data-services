package com.web.demo.config.wc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommonWebClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonWebClient.class);
   /* private final ClientAuditRepository logDetailsRepository;
    @Autowired
    private AuditExchangeFilter auditExchangeFilter;
    Map<String, ClientAudit> map = new HashMap();
    @Autowired
    private AuditService auditService;

    public CommonWebClient(ClientAuditRepository logDetailsRepository) {
        this.logDetailsRepository = logDetailsRepository;
    }*/

    public <S> S createClient(String url, Map<String, String> headersMap, Class<S> serviceType) {
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(createWebClient(url, headersMap)))
                .build()
                .createClient(serviceType);
    }

    private WebClient createWebClient(String url, Map<String, String> headers) {
        return WebClient.builder()
                .baseUrl(url)
                /*.filters(exchangeFilterFunctions -> {
                    exchangeFilterFunctions.add(new AuditClientFilter());
                })*/
                .defaultHeaders(header -> headers.forEach(header::add))
                .build();
    }

    /*

    public <T> T httpServiceProxyFactory(String baseUrl, Map<String, String> headers, Class<T> clientClass) {
        return (T) HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(this.createWebClient(baseUrl, headers)))
                .build()
                .createClient(clientClass);
    }

    public <T> T httpServiceProxyFactory(String baseUrl, Map<String, String> headers, Class<T> clientClass) {
        return (T) HttpServiceProxyFactory.builderFor(WebClientAdapter.create(this.createWebClient(baseUrl, headers, this.auditService))).build().createClient(clientClass);
    }

    public WebClient createWebClient(String baseUrl, Map<String, String> headers, AuditService auditService) {
        WebClient.Builder builder = WebClient.builder().baseUrl(baseUrl).filter((new LoggingFilter(this.logDetailsRepository)).logRequestAndResponse());
        Objects.requireNonNull(builder);
        headers.forEach((x$0, xva$1) -> builder.defaultHeader(x$0, new String[]{xva$1}));
        return builder.build();
    }

    private ExchangeFilterFunction logAndSaveRequestDetails(AuditService auditService) {
        return ExchangeFilterFunction.ofRequestProcessor((request) -> {
            String clientAuditId = UUID.randomUUID().toString();
            LOGGER.info("ExchangeFilterFunction logAndSaveRequestDetails entry point auditService::{} clientAuditId::{}", auditService, clientAuditId);
            ClientAudit audit = new ClientAudit();
            audit.setMethod(request.method().name());
            audit.setRequestHeaders(request.headers().toString());
            audit.setHostname(request.url().getHost());
            audit.setRequestBody(this.getBody(request));
            audit.setRequestTimestamp(LocalDateTime.now());
            audit.setRequestUrl(request.url().toString());
            LOGGER.info("ExchangeFilterFunction logAndSaveRequestDetails audit::{}", audit);
            this.map.put(clientAuditId, audit);
            return Mono.deferContextual((context) -> Mono.just(ClientRequest.from(request).build()).contextWrite((ctx) -> ctx.put("X-Request-UUID", clientAuditId)));
        });
    }

    private ExchangeFilterFunction logAndSaveResponseDetails(AuditService auditService) {
        return ExchangeFilterFunction.ofResponseProcessor((clientResponse) -> Mono.deferContextual((context) -> {
            String uuid = (String)context.getOrDefault("X-Request-UUID", (Object)null);
            LOGGER.info("ExchangeFilterFunction logAndSaveResponseDetails uuid::{}", uuid);
            return uuid != null ? clientResponse.bodyToMono(String.class).flatMap((responseBody) -> {
                ClientAudit clientAudit = (ClientAudit)this.map.get(uuid);
                clientAudit.setResponseStatus(clientResponse.statusCode().value());
                clientAudit.setResponse(responseBody);
                LOGGER.info("ExchangeFilterFunction logAndSaveResponseDetails clientAudit::{}", clientAudit);
                return Mono.just(clientAudit);
            }) : Mono.just(clientResponse);
        }).then(Mono.just(clientResponse)));
    }

    private ExchangeFilterFunction logAndSaveResponseDetailsTemp(AuditService auditService) {
        return ExchangeFilterFunction.ofResponseProcessor((clientResponse) -> clientResponse.bodyToMono(String.class).flatMap((responseBody) -> {
            ClientResponse.Headers headers = clientResponse.headers();
            LOGGER.info("ExchangeFilterFunction logAndSaveResponseDetails entry point headers::{}", headers.toString());
            String clientAuditId = clientResponse.headers().asHttpHeaders().getFirst("X-Request-UUID");
            LOGGER.info("ExchangeFilterFunction logAndSaveResponseDetails entry point clientAuditId::{}", clientAuditId);
            if (clientAuditId != null) {
                ClientAudit clientAudit = (ClientAudit)this.map.get(clientAuditId);
                clientAudit.setResponseStatus(clientResponse.statusCode().value());
                clientAudit.setResponseTimestamp(LocalDateTime.now());
                clientAudit.setResponse(responseBody);
                return Mono.just(clientAudit);
            } else {
                return Mono.empty();
            }
        }).then(Mono.just(clientResponse)));
    }

    private ExchangeFilterFunction logAndSaveErrorDetails(AuditService auditService) {
        return ExchangeFilterFunction.ofResponseProcessor((clientResponse) -> clientResponse.statusCode().isError() ? clientResponse.bodyToMono(String.class).flatMap((errorBody) -> {
            String clientAuditId = clientResponse.headers().asHttpHeaders().getFirst("X-Request-UUID");
            LOGGER.info("ExchangeFilterFunction logAndSaveErrorDetails entry point clientAuditId::{}", clientAuditId);
            return clientAuditId != null ? auditService.updateAuditLog(clientAuditId, (audit) -> {
                audit.setResponseStatus(clientResponse.statusCode().value());
                audit.setResponseTimestamp(LocalDateTime.now());
                audit.setErrorMessage(errorBody);
            }) : Mono.empty();
        }).then(Mono.just(clientResponse)) : Mono.just(clientResponse));
    }

    public ExchangeFilterFunction auditFilter() {
        return ExchangeFilterFunction.ofRequestProcessor((request) -> Mono.fromCallable(() -> {
            try {
                LOGGER.info("======auditFilter fromCallable entry point======");
                ClientAudit audit = new ClientAudit();
                audit.setMethod(request.method().name());
                audit.setRequestHeaders(request.headers().toString());
                audit.setHostname(request.url().getHost());
                audit.setRequestBody(this.getBody(request));
                audit.setRequestTimestamp(LocalDateTime.now());
                long id;
                if (this.auditService != null) {
                    audit = this.auditService.saveClientAudit(audit);
                    id = audit.getId();
                } else {
                    id = 0L;
                }

                return id;
            } catch (Exception e) {
                System.err.println("Audit save failed: " + e.getMessage());
                return -1L;
            }
        }).subscribeOn(Schedulers.boundedElastic()).flatMap((id) -> Mono.just(request).contextWrite((context) -> context.put("auditId", id)))).andThen((request, next) -> {
            LocalDateTime startTime = LocalDateTime.now();
            LOGGER.info("======auditFilter andThen entry point======");
            return next.exchange(request).flatMap((response) -> this.processResponse(response, startTime));
        });
    }

    private Mono<ClientResponse> processResponse(ClientResponse response, LocalDateTime startTime) {
        return response.bodyToMono(String.class).flatMap((body) -> {
            LOGGER.info("======processResponse flatMap entry point======");
            Long auditId = this.retrieveAuditId();
            if (this.auditService != null) {
                ClientAudit audit = (ClientAudit)this.auditService.findClientAuditById(auditId).orElseThrow();
                audit.setResponseStatus(response.statusCode().value());
                audit.setResponse(body);
                audit.setResponseTimestamp(LocalDateTime.now());
                this.auditService.saveClientAudit(audit);
            }

            LOGGER.info("======response======{}", response);
            return Mono.just(response);
        });
    }

    private Mono<ClientResponse> handleError(Throwable e, LocalDateTime startTime) {
        LOGGER.info("======handleError entry point======");
        Long auditId = 10L;
        if (this.auditService != null) {
            ClientAudit audit = (ClientAudit)this.auditService.findClientAuditById(auditId).orElseThrow();
            audit.setErrorMessage(e.getMessage());
            audit.setResponseTimestamp(LocalDateTime.now());
            this.auditService.saveClientAudit(audit);
        }

        return Mono.error(e);
    }

    private Long retrieveAuditId() {
        return (Long)Mono.deferContextual((ctx) -> Mono.justOrEmpty((Optional)ctx.get("auditId"))).subscribeOn(Schedulers.boundedElastic()).block();
    }

    private Mono<ClientResponse> processResponseTmp(ClientResponse response, LocalDateTime startTime) {
        return response.bodyToMono(String.class).flatMap((body) -> {
            Long auditId = this.retrieveAuditId();
            if (this.auditService != null) {
                ClientAudit audit = (ClientAudit)this.auditService.findClientAuditById(auditId).orElseThrow();
                audit.setResponseStatus(response.statusCode().value());
                audit.setResponse(body);
                audit.setResponseTimestamp(LocalDateTime.now());
                this.auditService.saveClientAudit(audit);
            }

            return Mono.just(response);
        });
    }

    private Mono<ClientResponse> handleErrorTmp(Throwable e, LocalDateTime startTime) {
        Long auditId = this.retrieveAuditId();
        if (this.auditService != null) {
            ClientAudit audit = (ClientAudit)this.auditService.findClientAuditById(auditId).orElseThrow();
            audit.setErrorMessage(e.getMessage());
            audit.setResponseTimestamp(LocalDateTime.now());
            this.auditService.saveClientAudit(audit);
        }

        return Mono.error(e);
    }*/

    private Long retrieveAuditIdTmp() {
        return (Long) Mono.deferContextual((ctx) -> Mono.justOrEmpty((Optional) ctx.get("auditId"))).block();
    }

    private String getBody(ClientRequest request) {
        return null;
    }
}
