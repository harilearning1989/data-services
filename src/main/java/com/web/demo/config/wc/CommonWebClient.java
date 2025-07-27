package com.web.demo.config.wc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.demo.exceptions.RemoteServiceException;
import com.web.demo.exceptions.ResourceNotFoundException;
import com.web.demo.exceptions.TimeoutException;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static reactor.core.Exceptions.unwrap;

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

    /*private WebClient createWebClient(String url, Map<String, String> headers) {
        return WebClient.builder()
                .baseUrl(url)
                *//*.filters(exchangeFilterFunctions -> {
                    exchangeFilterFunctions.add(new AuditClientFilter());
                })*//*
                .defaultHeaders(header -> headers.forEach(header::add))
                .filter(errorHandlingFilter())
                .build();
    }*/

    private WebClient createWebClient(String url, Map<String, String> headers) {
        // Step 1: Create connection pool
        ConnectionProvider connectionProvider = ConnectionProvider.builder("custom-conn-pool")
                .maxConnections(50)
                .pendingAcquireTimeout(Duration.ofSeconds(10))
                .maxIdleTime(Duration.ofSeconds(60))
                .build();

        // Step 2: Create HttpClient directly using connection provider
        HttpClient httpClient = HttpClient.create(connectionProvider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(5, TimeUnit.SECONDS))
                );

        // Step 3: Use this in WebClient
        return WebClient.builder()
                .baseUrl(url)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeaders(http -> headers.forEach(http::add))
                .filter(errorHandlingFilter())
                .build();
    }

    /*private ExchangeFilterFunction errorHandlingFilter() {
        return (request, next) -> {
            System.out.println("Request Thread: " + Thread.currentThread().getName());
            String url = request.url().toString(); // Capture the URL here

            return next.exchange(request).flatMap(response -> {
                HttpStatusCode status = response.statusCode();
                System.out.println("Response Thread: " + Thread.currentThread().getName());

                if (status.is4xxClientError()) {
                    if (status == HttpStatus.NOT_FOUND) {
                        return response.bodyToMono(String.class)
                                .defaultIfEmpty("Resource not found")
                                .flatMap(body -> Mono.error(new ResourceNotFoundException(url, status, body)));
                    }
                    return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Client error: " + body)));
                } else if (status.is5xxServerError()) {
                    return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Server error: " + body)));
                }

                return Mono.just(response);
            });
        };
    }*/

    private ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            return Mono.just(ClientRequest.from(request)
                    .attribute("requestUrl", request.url().toString())
                    .build());
        }).andThen((request, next) ->
                next.exchange(request)
                        .flatMap(response -> {
                            String requestUrl = request.attribute("requestUrl")
                                    .map(Object::toString)
                                    .orElse("unknown");

                            if (response.statusCode().is4xxClientError()) {
                                if (response.statusCode() == HttpStatus.NOT_FOUND) {
                                    return response.bodyToMono(String.class)
                                            .defaultIfEmpty("Resource not found")
                                            .flatMap(body -> Mono.error(new ResourceNotFoundException(
                                                    requestUrl,
                                                    HttpStatus.NOT_FOUND,
                                                    "404 NOT FOUND: " + body
                                            )));
                                }
                                return response.bodyToMono(String.class)
                                        .flatMap(body -> Mono.error(new RemoteServiceException(
                                                requestUrl,
                                                response.statusCode(),
                                                body
                                        )));
                            } else if (response.statusCode().is5xxServerError()) {
                                return response.bodyToMono(String.class)
                                        .flatMap(body -> Mono.error(new RemoteServiceException(
                                                requestUrl,
                                                response.statusCode(),
                                                extractErrorJson(body)
                                        )));
                            }
                            return Mono.just(response);
                        })
                        .onErrorResume(throwable -> {
                            String requestUrl = request.attribute("requestUrl")
                                    .map(Object::toString)
                                    .orElse("unknown");

                            Throwable cause = unwrap(throwable);
                            // Check if the exception is or wraps ReadTimeoutException
                           /* Throwable cause = throwable instanceof WebClientRequestException
                                    ? throwable.getCause()
                                    : throwable;*/

                            if (cause instanceof ReadTimeoutException) {
                                return Mono.error(new TimeoutException(
                                        requestUrl,
                                        HttpStatus.GATEWAY_TIMEOUT,
                                        "Read timed out while calling: " + requestUrl
                                ));
                            }

                            // Check if error message has embedded JSON and format it
                           /* String message = cause.getMessage();
                            if (message != null && message.contains("{") && message.contains("error")) {
                                try {
                                    String jsonPart = message.substring(message.indexOf("{"));
                                    String formatted = extractErrorJson(jsonPart);
                                    return Mono.error(new RuntimeException("Parsed error: " + formatted));
                                } catch (Exception ex) {
                                    // Fallback if parsing fails
                                    return Mono.error(new RuntimeException("Unhandled error: " + message));
                                }
                            }*/

                            String message = cause.getMessage();
                            if (message != null && message.contains("{") && message.contains("error")) {
                                try {
                                    String jsonPart = message.substring(message.indexOf("{"));
                                    String formatted = extractErrorJson(jsonPart);
                                    return Mono.error(new RemoteServiceException(
                                            requestUrl,
                                            HttpStatus.INTERNAL_SERVER_ERROR,
                                            formatted
                                    ));
                                } catch (Exception ex) {
                                    return Mono.error(new RemoteServiceException(
                                            requestUrl,
                                            HttpStatus.INTERNAL_SERVER_ERROR,
                                            message
                                    ));
                                }
                            }

                            return Mono.error(new RemoteServiceException(
                                    requestUrl,
                                    HttpStatus.INTERNAL_SERVER_ERROR,
                                    "Internal Server Error"
                            ));
                            //return Mono.error(throwable);
                        })
        );
    }

    private String extractErrorJson(String rawJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> json = mapper.readValue(rawJson, Map.class);

            return String.format(
                    "timestamp: %s, status: %s, error: %s, path: %s",
                    json.getOrDefault("timestamp", ""),
                    json.getOrDefault("status", ""),
                    json.getOrDefault("error", ""),
                    json.getOrDefault("path", "")
            );
        } catch (Exception e) {
            return rawJson; // fallback if not parseable
        }
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
