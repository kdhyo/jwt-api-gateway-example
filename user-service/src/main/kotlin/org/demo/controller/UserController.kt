package org.demo.controller

import org.demo.entity.AuthRequest
import org.demo.entity.UserVO
import org.demo.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun save(@RequestBody request: AuthRequest): ResponseEntity<UserVO> {
        return ResponseEntity.ok(userService.save(request))
    }

    @GetMapping("/secured")
    fun secured(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello from secured endpoint")
    }
}
