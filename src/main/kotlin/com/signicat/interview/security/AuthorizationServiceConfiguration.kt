package com.signicat.interview.security

import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.jwk.gen.ECKeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthorizationServiceConfiguration {

    @Bean
    fun authorizationService(tokenFactory: TokenFactory): AuthorizationService = AuthorizationService(tokenFactory)

    @Bean
    fun tokenFactory(): TokenFactory {
        val key = ECKeyGenerator(Curve.P_256)
            .keyID("afabiszewski-app-pub-key")
            .generate()

        return TokenFactory(key)
    }
}