package org.demo.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtUtils(
    @Value("\${jwt.secret}")
    private val secret: String
) {

    private val key: Key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun getClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }

    fun isExpired(token: String): Boolean {
        return try {
            getClaims(token).expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}
