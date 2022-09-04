package com.signicat.interview.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class WrongPasswordException(override val message: String?) : Exception(message) {
}