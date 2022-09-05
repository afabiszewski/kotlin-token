package com.signicat.interview.security

class AuthorizationService(private val tokenFactory: TokenFactory) {

    fun generateToken(): String = tokenFactory.generate()

    fun generateTokenForUser(userId: Long, userName: String, groups: Map<Long, String>): String =
        tokenFactory.generateForUser(userId, userName, groups)
}