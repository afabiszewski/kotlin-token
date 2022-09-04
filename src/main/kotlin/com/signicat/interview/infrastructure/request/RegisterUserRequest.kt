package com.signicat.interview.infrastructure.request

data class RegisterUserRequest(
    val name: String,
    val password: String
)