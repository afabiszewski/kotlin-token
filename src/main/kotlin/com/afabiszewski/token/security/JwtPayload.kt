package com.afabiszewski.token.security

data class JwtPayload(
    val sub: String,
    val username: String,
    val groups: List<GroupsPayload>
) {
    data class GroupsPayload(
        val id: String,
        val name: String
    )
}