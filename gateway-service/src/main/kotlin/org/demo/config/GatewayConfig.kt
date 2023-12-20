package org.demo.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfig(
    private val filter: AuthenticationFilter
) {

    @Bean
    fun routes(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route("user-service") { r ->
                r.path("/users/**")
                    .filters { f -> f.filter(filter) }
                    .uri("lb://user-service")
            }
            .route("auth-service") { r ->
                r.path("/auth/**")
                    .filters { f -> f.filter(filter) }
                    .uri("lb://auth-service")
            }
            .build()
    }
}
