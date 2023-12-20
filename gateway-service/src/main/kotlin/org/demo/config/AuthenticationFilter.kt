package org.demo.config

import org.demo.service.JwtUtils
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@RefreshScope
@Component
class AuthenticationFilter(
    private val validator: RouterValidator,
    private val jwtUtils: JwtUtils
) : GatewayFilter {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        if (validator.isSecured().test(request)) {
            val token = request.headers.getFirst("Authorization")
            if (token == null || jwtUtils.isExpired(token)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED)
            }
        }
        return chain.filter(exchange)
    }

    private fun onError(exchange: ServerWebExchange, httpStatus: HttpStatus): Mono<Void> {
        val response = exchange.response
        response.statusCode = httpStatus
        return response.setComplete()
    }

}
