package com.afabiszewski.token.security

import com.nimbusds.jose.shaded.json.JSONArray
import com.nimbusds.jose.shaded.json.JSONObject
import com.nimbusds.jwt.SignedJWT

class AuthorizationService(private val tokenFactory: TokenFactory) {

    fun generateTokenForUser(userId: Long, userName: String, groups: Map<Long, String>): String =
        tokenFactory.generateForUser(userId, userName, groups)

    fun isUserAuthorizedForAction(token: String): Boolean {
        val signedJWT = SignedJWT.parse(token)
        val jwtPayload = signedJWT.jwtClaimsSet.getJSONObjectClaim("jwtPayload")
        val groups = jwtPayload["groups"] as JSONArray
        groups
            .map { group -> group as JSONObject }
            .map { group -> group.getAsString("name") }
            .filter { groupName -> groupName == "admin" }
            .ifEmpty { return false }
        return true
    }
}