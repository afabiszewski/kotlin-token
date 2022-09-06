package com.afabiszewski.token.infrastructure.request

data class RegisterUserRequest(
    val name: String,
    val password: String,
    val groups: List<String>
)