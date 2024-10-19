package com.fabianbah.gateway.beans;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class GatewayBeans {
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
}
