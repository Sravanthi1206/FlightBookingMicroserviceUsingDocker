package com.apigateway.config;


import com.apigateway.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public RouteLocator configure(RouteLocatorBuilder builder) {
        return builder.routes()

            .route("auth-service", r -> r.path("/api/auth/**")
                    .uri("lb://auth-service"))
            .route("flight-service", r -> r.path("/api/flights/**")
                    .uri("lb://flight-service"))

            .route("booking-service", r -> r.path("/api/bookings/**")
                    .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthFilter.Config())))
                    .uri("lb://booking-service"))
            .route("email-service", r -> r.path("/api/email/**")
                    .uri("lb://email-service"))

            .build();
    }
}


