package com.signicat.interview.infrastructure

import com.signicat.interview.domain.UserApplication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController (val userApplication : UserApplication) {
    
    @PostMapping("/users")
    fun createUser(userRequest: UserRequest) : String {
        return userApplication.createUser(userRequest.name, userRequest.password)
    }

}