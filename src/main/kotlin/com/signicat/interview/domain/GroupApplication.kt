package com.signicat.interview.domain

import kotlin.random.Random

class GroupApplication internal constructor(private val groupRepository: GroupRepository) {

    fun getGroup(groupName: String) {
    }

    fun createGroup(groupName: String): Long {
        val userGroup = UserGroup(Random.nextLong(), groupName)
        return groupRepository.save(userGroup).id
    }

    fun updateGroup(groupName: String): Long {
        val userGroup = UserGroup(Random.nextLong(), groupName)
        return groupRepository.save(userGroup).id
    }

    fun deleteGroup(groupName: String): Long {
        val userGroup = UserGroup(Random.nextLong(), groupName)
        return groupRepository.save(userGroup).id
    }

    internal fun getOrCreateGroups(groupNames: Set<String>): Set<UserGroup> {
        return groupNames
            .map { groupName ->
                groupRepository.findByName(groupName) ?: groupRepository.save(UserGroup(name = groupName))
            }.toSet()
    }
}