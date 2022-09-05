package com.signicat.interview.security

import com.nimbusds.jose.shaded.json.JSONArray
import com.nimbusds.jwt.SignedJWT

class AuthorizationService(private val tokenFactory: TokenFactory) {

    fun generateTokenForUser(userId: Long, userName: String, groups: Map<Long, String>): String =
        tokenFactory.generateForUser(userId, userName, groups)

    fun isUserAuthorizedToAction(token: String): Boolean {
        val signedJWT = SignedJWT.parse(token)
        val jwtPayload = signedJWT.jwtClaimsSet.getJSONObjectClaim("jwtPayload")
        val groups = jwtPayload["groups"] as JSONArray
        groups.filter { !equals("admin") }.ifEmpty { return false }
        return true
    }
}