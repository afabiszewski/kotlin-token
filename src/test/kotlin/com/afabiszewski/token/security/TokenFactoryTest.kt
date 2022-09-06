package com.afabiszewski.token.security

import com.nimbusds.jose.crypto.ECDSAVerifier
import com.nimbusds.jose.jwk.Curve.P_256
import com.nimbusds.jose.jwk.gen.ECKeyGenerator
import com.nimbusds.jwt.SignedJWT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant.now
import java.util.Date.from

class TokenFactoryTest {

    private val key = ECKeyGenerator(P_256)
        .keyID("0")
        .generate()

    private val underTest = TokenFactory(key)

    @Test
    fun `generate token`() {
        val token = underTest.generate()

        val signedJWT = SignedJWT.parse(token)

        assertEquals(
            true,
            signedJWT.jwtClaimsSet.expirationTime.after(
                from(now())
            )
        )
    }

    @Test
    fun `verify token`() {
        val token = underTest.generate()

        val signedJWT = SignedJWT.parse(token)

        assertEquals(
            true,
            signedJWT.verify(
                ECDSAVerifier(key.toECPublicKey())
            )
        )
    }
}
