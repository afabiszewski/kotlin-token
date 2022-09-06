package com.signicat.interview.domain

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GroupApplicationTest {

    private val groupRepository = mockk<GroupRepository>()
    private val groupApplication = GroupApplication(groupRepository)

    @Test
    fun `get group`() {
        val groupname = "testGroup"
        every { groupRepository.findByName(groupname) } returns UserGroup(1, groupname)

        val fetchedGroup = groupApplication.getGroup(groupname)

        Assertions.assertEquals(
            groupname,
            fetchedGroup
        )
    }

    fun `create group`() {

    }

    fun `delete group`() {

    }
}