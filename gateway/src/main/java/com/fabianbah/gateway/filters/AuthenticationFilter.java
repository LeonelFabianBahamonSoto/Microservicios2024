package com.fabianbah.gateway.filters;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;

import com.fabianbah.gateway.dtos.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GatewayFilter {

    @Value("${security.auth-server.url}")
    private String AUTH_VALIDATE_URI;

    @Value("${security.auth-server.access-token-header}")
    private String ACCESS_TOKEN_HEADER_NAME;

    private final WebClient webClient;

    public AuthenticationFilter() {
        this.webClient = WebClient.builder().build();
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        List<String> publicEndpoints = List.of(
                "/auth-server/users/createUser", "/auth-server/welcome", "/auth-server/aboutUs",
                "/auth-server/auth/authenticate", "/auth-server/auth/validateTokenAuth");

        String path = exchange.getRequest().getURI().getPath();

        if (!publicEndpoints.contains(path)
                && !exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return this.onError(exchange, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
        }

        List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        String tokenHeader = "";
        String[] chunks;
        String token = "";

        if (authHeaders != null && !authHeaders.isEmpty()) {
            tokenHeader = authHeaders.get(0);
            chunks = tokenHeader.split(" ");

            if (chunks.length != 2 || !chunks[0].equals("Bearer")) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
            }

            token = chunks[1];
        }

        return this.webClient
                .post()
                .uri(AUTH_VALIDATE_URI)
                .header(ACCESS_TOKEN_HEADER_NAME, token)
                .retrieve()
                .bodyToMono(TokenDto.class)
                .map(response -> exchange)
                .flatMap(chain::filter)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    return this.onError( exchange, ex.getStatusCode(), ex.getResponseBodyAsString() );
                });
    };

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatusCode httpStatusCode, String errorMessage ) {
        final var response = exchange.getResponse();
        response.setStatusCode(httpStatusCode);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", new Date().toString());
        errorResponse.put("message", errorMessage);

        try {
            // Convertir el objeto de respuesta de error a bytes
            byte[] bytes = new ObjectMapper().writeValueAsBytes(errorResponse);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);

            // Establecer el contenido de la respuesta como JSON
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return response.setComplete();
        }
    }
}
