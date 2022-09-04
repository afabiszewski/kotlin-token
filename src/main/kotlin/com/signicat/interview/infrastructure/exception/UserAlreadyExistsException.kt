package com.signicat.interview.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class UserAlreadyExistsException(override val message: String?) : Exception(message) {
}