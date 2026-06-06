package com.example.apigateway;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> receptionRoute() {
        return GatewayRouterFunctions.route("reception-service")
                .route(
                        RequestPredicates.path("/reception/**"),
                        HandlerFunctions.http()
                )
                .before(
                        BeforeFilterFunctions.uri(
                                "http://localhost:8081"
                        )
                )
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> housekeepingRoute() {
        return GatewayRouterFunctions.route("housekeeping-service")
                .route(
                        RequestPredicates.path("/housekeeping/**"),
                        HandlerFunctions.http()
                )
                .before(
                        BeforeFilterFunctions.uri(
                                "http://localhost:8082"
                        )
                )
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> maintenanceRoute() {
        return GatewayRouterFunctions.route("maintenance-service")
                .route(
                        RequestPredicates.path("/maintenance/**"),
                        HandlerFunctions.http()
                )
                .before(
                        BeforeFilterFunctions.uri(
                                "http://localhost:8083"
                        )
                )
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> roomServiceRoute() {
        return GatewayRouterFunctions.route("room-service")
                .route(
                        RequestPredicates.path("/room-service/**"),
                        HandlerFunctions.http()
                )
                .before(
                        BeforeFilterFunctions.uri(
                                "http://localhost:8084"
                        )
                )
                .build();
    }
}