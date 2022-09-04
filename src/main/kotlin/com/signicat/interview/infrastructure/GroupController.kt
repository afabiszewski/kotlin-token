package com.signicat.interview.infrastructure

import com.signicat.interview.domain.GroupApplication
import com.signicat.interview.infrastructure.request.CreateGroupRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupController(internal val groupApplication: GroupApplication) {

    @GetMapping("/groups/{name}")
    fun getGroup(@PathVariable name: String): String {
        return groupApplication.getGroup(name)
    }

    @PostMapping("/groups", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createGroup(@RequestBody createGroupRequest: CreateGroupRequest): String {
        return groupApplication.createGroup(createGroupRequest.name)
    }

    @DeleteMapping("/groups/{name}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun removeGroup(@PathVariable name: String): String {
        return groupApplication.deleteGroup(name)
    }

}