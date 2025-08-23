package com.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

    @Bean
    public RouteLocator badriBankCustomRoute(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/badriBank/api/accounts/**")
                        .filters(f -> f.rewritePath("/badriBank/api/accounts/(?<segment>.*)","/api/accounts/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://ACCOUNTS")
                )
                .route(p -> p
                        .path("/badriBank/loans/**")
                        .filters(f -> f.rewritePath("/badriBank/loans/(?<segment>.*)","/${segment}"))
                        .uri("lb://LOANS")
                )
                .route(p -> p
                        .path("/badriBank/cards/**")
                        .filters(f -> f.rewritePath("/badriBank/cards/(?<segment>.*)","/${segment}"))
                        .uri("lb://CARDS")
                )
                .build();
    }

}
