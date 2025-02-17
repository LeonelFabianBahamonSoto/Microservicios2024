package com.fabianbah.gateway.beans;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fabianbah.gateway.filters.AuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class GatewayBeans {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    @Profile( value = "eureka-off" )
    RouteLocator routeLocatorEurekaOff( RouteLocatorBuilder routeLocatorBuilder )
    {
        return routeLocatorBuilder
                .routes()
                .route(
                    route -> route
                        .path("/users/**")
                        .uri("http://localhost:8080") )
                // .route(
                //     route -> route
                //         .path("/report/**")
                //         .uri("http://localhost:7070")
                // )
            .build();
    }

    @Bean
    @Profile( value = "eureka-on" )
    RouteLocator routeLocatorEurekaOn( RouteLocatorBuilder routeLocatorBuilder )
    {
        return routeLocatorBuilder.routes()
                .route(
                    route -> route
                        .path("/auth-server/auth/authenticate")
                        .uri("lb://authorization-server")
                )
                .route(
                    route -> route
                        .path("/auth-server/**")
                        .filters( filter -> filter.filter(this.authenticationFilter))
                        .uri("lb://authorization-server")
                )
                .route(
                    route -> route
                        .path("/users/**")
                        .filters( filter -> filter.filter(this.authenticationFilter))
                        .uri("http://localhost:8080")
                )
            .build();
    }

    @Bean
    @Profile( value = "auth" )
    RouteLocator routeLocatorAuth( RouteLocatorBuilder routeLocatorBuilder )
    {
        return routeLocatorBuilder
                .routes()
                .route(
                    route -> route
                        .path("/users/**")
                        .filters(filter -> filter.filter(this.authenticationFilter))
                        .uri("http://localhost:8080") )
                .route(route -> route
                        .path("/auth-server/**")
                        .uri("http://localhost:3030"))
                // .route(
                //     route -> route
                //         .path("/report/**")
                //         .uri("http://localhost:7070")
                // )
            .build();
    }
}
