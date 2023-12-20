package org.demo.config

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service
import java.util.function.Predicate

@Service
class RouterValidator {

    val openEndpoint = listOf(
        "/auth/register",
    )

    fun isSecured(): Predicate<ServerHttpRequest> = Predicate {
        val path = it.uri.path
        !openEndpoint.contains(path)
    }
}
