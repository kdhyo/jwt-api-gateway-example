package org.demo.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtUtil(
    @Value("\${jwt.secret}")
    private val secret: String,
    @Value("\${jwt.expiration}")
    private val expiration: String
) {

    private val key: Key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun getClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).body
    }

    fun getExpirationDate(token: String): Date = getClaims(token).expiration

    fun isExpired(token: String): Boolean = getExpirationDate(token).before(Date())

    fun generate(userId: String, role: String, tokenType: String): String {
        val claims = mapOf("id" to userId, "role" to role)

        val baseExpiration = expiration.toInt() * 1000
        val expMillis = baseExpiration * (tokenType.equals("ACCESS", ignoreCase = true).takeIf { it }?.let { 1 } ?: 5)

        val now = Date()
        val exp = Date(now.time + expMillis)

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(claims["id"])
            .setIssuedAt(now)
            .setExpiration(exp)
            .signWith(key)
            .compact()
    }
}
