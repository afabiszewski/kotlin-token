package com.afabiszewski.token.security

import com.nimbusds.jose.crypto.ECDSAVerifier
import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.jwk.gen.ECKeyGenerator
import com.nimbusds.jwt.SignedJWT
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AuthorizationServiceTest {

    private val key = ECKeyGenerator(Curve.P_256)
        .keyID("0")
        .generate()
    private val tokenFactory = TokenFactory(key)
    private val authorizationService = AuthorizationService(tokenFactory)

    @Test
    fun `generate token for user returns valid token`() {
        val username = "testUsername"
        val groupsMap = HashMap<Long, String>()
        groupsMap[1] = "testGroup"

        val token = authorizationService.generateTokenForUser(1, username, groupsMap)

        val signedJWT = SignedJWT.parse(token)
        Assertions.assertEquals(
            true,
            signedJWT.verify(
                ECDSAVerifier(key.toECPublicKey())
            )
        )
    }

    @Test
    fun `is user authorized for action returns true for admin user`() {
        val username = "testUsername"
        val groupsMap = HashMap<Long, String>()
        groupsMap[1] = "admin"
        val token = authorizationService.generateTokenForUser(1, username, groupsMap)

        val isUserAuthorized = authorizationService.isUserAuthorizedForAction(token)

        Assertions.assertEquals(
            true,
            isUserAuthorized
        )
    }

    @Test
    fun `is user authorized for action returns true for non-admin user`() {
        val username = "testUsername"
        val groupsMap = HashMap<Long, String>()
        groupsMap[1] = "testGroup"
        val token = authorizationService.generateTokenForUser(1, username, groupsMap)

        val isUserAuthorized = authorizationService.isUserAuthorizedForAction(token)

        Assertions.assertEquals(
            false,
            isUserAuthorized
        )
    }
}