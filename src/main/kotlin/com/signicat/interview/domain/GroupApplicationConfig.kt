package com.signicat.interview.domain

import org.springframework.context.annotation.Configuration

@Configuration
internal class GroupApplicationConfig {

    fun groupApplication(groupRepository: GroupRepository) : GroupApplication = GroupApplication(groupRepository)
}