package com.signicat.interview.infrastructure

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(username: String) : Exception("Username:${username} not found") {
}