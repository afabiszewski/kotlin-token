package com.signicat.interview.infrastructure

import com.signicat.interview.domain.UserApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController (internal val userApplication : UserApplication) {

    @PostMapping("/users", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(@RequestBody userRequest: UserRequest) : String {
        return userApplication.registerUser(userRequest.name, userRequest.password)
    }

    @GetMapping("/hello")
    fun hello() : String {
        return "hello\n"
    }
}