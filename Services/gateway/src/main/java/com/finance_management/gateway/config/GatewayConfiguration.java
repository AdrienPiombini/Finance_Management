package com.finance_management.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfiguration {
        @Autowired
        AuthenticationFilter filter;

        @Bean
        public RouteLocator routes(RouteLocatorBuilder builder) {
            return builder.routes()
                    .route("accounts-routes", r -> r.path("/accounts/**")
                            .filters(f -> f
                                    .filter(filter)
                                    .prefixPath("/api")
                                    .addResponseHeader("X-Powered-By", "Gateway Service"))
                            .uri("http://localhost:8081"))

                    .route("transactions-routes", r -> r.path("/transactions/**")
                            .filters(f -> f
                                    .filter(filter)
                                    .prefixPath("/api")
                                    .addResponseHeader("X-Powered-By", "Gateway Service"))
                            .uri("http://localhost:8081"))

                    .route("authentications-routes", r -> r.path("/authentication/**")
                            .filters(f -> f
                                    .filter(filter)
                                    .prefixPath("/api")
                                    .addResponseHeader("X-Powered-By", "Gateway Service"))
                            .uri("http://localhost:8082"))

                    .build();
        }

}
