package com.signicat.interview.domain

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class GroupApplicationConfig {

    @Bean
    fun groupApplication(groupRepository: GroupRepository): GroupApplication = GroupApplication(groupRepository)
}