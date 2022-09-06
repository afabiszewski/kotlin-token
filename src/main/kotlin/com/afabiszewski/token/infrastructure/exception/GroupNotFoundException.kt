package com.afabiszewski.token.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class GroupNotFoundException(override val message: String?) : Exception(message) {
}