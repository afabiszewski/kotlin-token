package com.signicat.interview.infrastructure.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class UserAlreadyExistsException(override val message: String?) : Exception(message) {
}