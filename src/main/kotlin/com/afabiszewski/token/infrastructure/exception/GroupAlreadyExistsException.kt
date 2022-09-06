package com.afabiszewski.token.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class GroupAlreadyExistsException(override val message: String?) : Exception(message) {
}