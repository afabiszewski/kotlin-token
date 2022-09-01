package com.signicat.interview.domain

internal data class Subject(
    val id: Int,
    val username: String,
    val password: String,
    val groups: Set<UserGroup>
)
