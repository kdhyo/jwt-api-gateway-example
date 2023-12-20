package org.demo.service

import org.demo.entities.AuthRequest
import org.demo.entities.AuthResponse
import org.demo.entities.UserVO
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AuthService(
    private val jwtUtil: JwtUtil
) {

    fun register(request: AuthRequest): AuthResponse {
        // do validation if user exists in DB
        request.password = BCrypt.hashpw(request.password, BCrypt.gensalt())

        val registeredUser: UserVO =
            RestTemplate().postForObject("http://localhost:9002/users", request, UserVO::class.java)!!

        val accessToken = jwtUtil.generate(registeredUser.id!!, registeredUser.role!!, "ACCESS")
        val refreshToken = jwtUtil.generate(registeredUser.id!!, registeredUser.role!!, "REFRESH")

        return AuthResponse(accessToken, refreshToken)
    }
}
