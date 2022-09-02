package com.signicat.interview.domain

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class UserApplicationConfig {

    @Bean
    fun userApplication(subjectRepository: SubjectRepository, groupRepository: GroupRepository) : UserApplication = UserApplication(subjectRepository, groupRepository)
}