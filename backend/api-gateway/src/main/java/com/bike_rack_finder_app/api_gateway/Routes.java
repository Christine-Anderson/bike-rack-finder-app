package com.bike_rack_finder_app.api_gateway;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> reportingServiceRoute() {
        return GatewayRouterFunctions.route("reporting_service")
                .route(RequestPredicates.POST("/report"), HandlerFunctions.http("http://localhost:8080"))
                .route(RequestPredicates.GET("/report/{reportId}"), HandlerFunctions.http("http://localhost:8080"))
                .route(RequestPredicates.PUT("/report/{reportId}"), HandlerFunctions.http("http://localhost:8080"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "reportingServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bikeRackServiceRoute() {
        return GatewayRouterFunctions.route("bike_rack_service")
                .route(RequestPredicates.GET("/bikeRack"), HandlerFunctions.http("http://localhost:8081"))
                .route(RequestPredicates.GET("/bikeRack/{rackId}"), HandlerFunctions.http("http://localhost:8081"))
                .route(RequestPredicates.PUT("/bikeRack/{rackId}/rating"), HandlerFunctions.http("http://localhost:8081"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker(
                        "bikeRackServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")
                ))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Service Unavailable, please try again later"))
                .build();
    }
}
