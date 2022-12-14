package com.afabiszewski.token.security

import com.nimbusds.jose.JOSEObjectType.JWT
import com.nimbusds.jose.JWSAlgorithm.ES256
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.ECDSASigner
import com.nimbusds.jose.jwk.ECKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import java.time.Instant.now
import java.util.*
import java.util.Date.from

const val TOKEN_EXPIRATION_TIME = 600L

class TokenFactory(
    private val key: ECKey
) {
    fun generate(): String = SignedJWT(
        createHeader(), createClaimsSet()
    ).run {
        sign(ECDSASigner(key.toECPrivateKey()))
        serialize()
    }

    fun generateForUser(userId: Long, userName: String, groups: Map<Long, String>): String =
        SignedJWT(
            createHeader(),
            createClaimsSetForUser(userId, userName, groups)
        ).run {
            sign(ECDSASigner(key.toECPrivateKey()))
            serialize()
        }

    private fun createHeader() = JWSHeader.Builder(ES256).type(JWT).keyID(key.keyID).build()

    private fun createClaimsSet() = JWTClaimsSet.Builder().claim("key", "value").expirationTime(
        from(
            now().plusSeconds(TOKEN_EXPIRATION_TIME)
        )
    ).build()

    private fun createClaimsSetForUser(userId: Long, userName: String, groups: Map<Long, String>): JWTClaimsSet {

        data class ClaimGroup(val id: String, val name: String)

        val claimGroups: List<JwtPayload.GroupsPayload> =
            groups.map { (groupId, groupName) -> JwtPayload.GroupsPayload(groupId.toString(), groupName) }
                .toCollection(LinkedList<JwtPayload.GroupsPayload>())

        val jwtPayload = JwtPayload(sub = userId.toString(), username = userName, groups = claimGroups)

        return JWTClaimsSet.Builder()
            .claim("jwtPayload", jwtPayload)
            .build()
    }
}
