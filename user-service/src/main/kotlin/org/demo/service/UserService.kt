package org.demo.service

import org.demo.entity.AuthRequest
import org.demo.entity.UserVO
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {

    fun save(userVO: AuthRequest): UserVO {
        // simulate save operation
        val userId = Date().time.toString()

        // save user in DB
        return UserVO(
            id = userId,
            email = userVO.email,
            password = userVO.password,
            role = "USER"
        )
    }
}
