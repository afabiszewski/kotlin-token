package com.signicat.interview.domain

import kotlin.random.Random

class GroupApplication internal constructor(private val groupRepository: GroupRepository) {

    fun createGroup(groupName: String) : Long {
        val userGroup = UserGroup(Random.nextLong(), groupName)
        return groupRepository.save(userGroup).id
    }
}