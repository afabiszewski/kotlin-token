package com.signicat.interview.infrastructure

import com.signicat.interview.domain.GroupApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupController(internal val groupApplication: GroupApplication) {

    @GetMapping("/groups/{name}")
    fun getGroup(): String {
        return "hello"
    }

    @PostMapping("/groups", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createGroup(@RequestBody registerUserRequest: RegisterUserRequest): String {
        return "OK"
    }

    @PutMapping("/groups/{name}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateGroup(@PathVariable name: String, @RequestBody signInRequest: SignInRequest): String {
        return "OK"
    }


    @DeleteMapping("/groups/{name}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun removeGroup(@PathVariable name: String, @RequestBody signInRequest: SignInRequest): String {
        return "OK"
    }

}