package com.signicat.interview.infrastructure

import com.signicat.interview.domain.GroupApplication
import com.signicat.interview.infrastructure.exception.UserUnauthorized
import com.signicat.interview.infrastructure.request.CreateGroupRequest
import com.signicat.interview.security.AuthorizationService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupController(
    internal val groupApplication: GroupApplication,
    internal val authorizationService: AuthorizationService
) {

    @GetMapping("/groups/{name}")
    fun getGroup(@PathVariable name: String, @RequestHeader(name = "Authorization") token: String): String {
        if (!authorizationService.isUserAuthorizedForAction(token)) throw UserUnauthorized("User is unauthorized")
        return groupApplication.getGroup(name)
    }

    @PostMapping("/groups", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createGroup(
        @RequestBody createGroupRequest: CreateGroupRequest, @RequestHeader(name = "Authorization") token: String
    ): String {
        if (!authorizationService.isUserAuthorizedForAction(token)) throw UserUnauthorized("User is unauthorized")
        return groupApplication.createGroup(createGroupRequest.name)
    }

    @DeleteMapping("/groups/{name}")
    fun removeGroup(@PathVariable name: String, @RequestHeader(name = "Authorization") token: String): String {
        if (!authorizationService.isUserAuthorizedForAction(token)) throw UserUnauthorized("User is unauthorized")
        return groupApplication.deleteGroup(name)
    }
}