package com.signicat.interview.infrastructure

import com.signicat.interview.domain.UserApplication
import com.signicat.interview.infrastructure.request.RegisterUserRequest
import com.signicat.interview.infrastructure.request.SignInRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(internal val userApplication: UserApplication) {

    @PostMapping("/users", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerUser(@RequestBody registerUserRequest: RegisterUserRequest): String {
        return userApplication.registerUser(
            registerUserRequest.name,
            registerUserRequest.password,
            registerUserRequest.groups
        )
    }

    @PutMapping("/users/{name}/token", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun signInUser(@PathVariable name: String, @RequestBody signInRequest: SignInRequest): String {
        return userApplication.signInUser(name, signInRequest.password)
    }

    @GetMapping("/hello")
    fun hello(): String {
        return "hello"
    }
}