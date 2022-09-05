package com.signicat.interview.domain

import com.signicat.interview.security.AuthorizationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class UserApplicationConfig {

    @Bean
    fun userApplication(
        subjectRepository: SubjectRepository,
        groupApplication: GroupApplication,
        authorizationService: AuthorizationService
    ): UserApplication =
        UserApplication(subjectRepository, groupApplication, authorizationService)
}
