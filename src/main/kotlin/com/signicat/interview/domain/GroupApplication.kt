package com.signicat.interview.domain

import com.signicat.interview.infrastructure.exception.GroupAlreadyExistsException
import com.signicat.interview.infrastructure.exception.GroupNotFoundException

class GroupApplication internal constructor(private val groupRepository: GroupRepository) {

    fun getGroup(groupName: String): String {
        return groupRepository.findByName(groupName)?.name
            ?: throw GroupNotFoundException("Group $groupName not found!")
    }

    fun createGroup(groupName: String): String {
        val userGroup = UserGroup(name = groupName)
        groupRepository.findByName(groupName)
            ?.let { throw GroupAlreadyExistsException("Group $groupName already exists!") }
        return groupRepository.save(userGroup).name
    }

    fun deleteGroup(groupName: String): String {
        val group = groupRepository.findByName(groupName)
            ?: throw GroupNotFoundException("Group $groupName not found!")
        groupRepository.delete(group)
        return group.name
    }

    internal fun getOrCreateGroups(groupNames: Set<String>): Set<UserGroup> {
        return groupNames.map { groupName ->
            groupRepository.findByName(groupName) ?: groupRepository.save(UserGroup(name = groupName))
        }.toSet()
    }
}