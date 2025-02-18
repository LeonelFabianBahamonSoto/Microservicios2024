package com.fabianbah.gateway.beans;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.fabianbah.gateway.filters.AuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class GatewayBeans {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Configuración de CORS
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://myweb.com");

        // Headers específicos en lugar de "*"
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("Accept");
        config.addAllowedHeader("Origin");
        config.addAllowedHeader("X-Requested-With");

        // Métodos permitidos
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("PATCH");

        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        // Otras configuraciones importantes
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        // Configuración de la fuente
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    @Bean
    @Profile(value = "eureka-off")
    RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder
                .routes()
                .route(
                    route -> route
                            .path("/users/**")
                            .uri("http://localhost:8080"))
                // .route(
                // route -> route
                // .path("/report/**")
                // .uri("http://localhost:7070")
                // )
                .build();
    }

    @Bean
    @Profile(value = "eureka-on")
    RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(
                        route -> route
                                .path("/auth-server/auth/authenticate")
                                .uri("lb://authorization-server"))
                .route(
                        route -> route
                                .path("/auth-server/**")
                                .filters(filter -> filter.filter(this.authenticationFilter))
                                .uri("lb://authorization-server"))
                .route(
                        route -> route
                                .path("/users/**")
                                .filters(filter -> filter.filter(this.authenticationFilter))
                                .uri("http://localhost:8080"))
                .build();
    }

    @Bean
    @Profile(value = "auth")
    RouteLocator routeLocatorAuth(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder
                .routes()
                .route(
                        route -> route
                                .path("/users/**")
                                .filters(filter -> filter.filter(this.authenticationFilter))
                                .uri("http://localhost:8080"))
                .route(route -> route
                        .path("/auth-server/**")
                        .uri("http://localhost:3030"))
                // .route(
                // route -> route
                // .path("/report/**")
                // .uri("http://localhost:7070")
                // )
                .build();
    }
}
