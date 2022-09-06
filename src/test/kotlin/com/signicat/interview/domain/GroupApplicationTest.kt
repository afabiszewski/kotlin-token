package com.signicat.interview.domain

import com.signicat.interview.infrastructure.exception.GroupAlreadyExistsException
import com.signicat.interview.infrastructure.exception.GroupNotFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class GroupApplicationTest {

    private val groupRepository = mockk<GroupRepository>()
    private val groupApplication = GroupApplication(groupRepository)

    @Test
    fun `get group returns saved group`() {
        val groupname = "testGroup"
        every { groupRepository.findByName(groupname) } returns UserGroup(1, groupname)

        val fetchedGroup = groupApplication.getGroup(groupname)

        Assertions.assertEquals(
            groupname, fetchedGroup
        )
    }

    @Test
    fun `get group throws error when fetching group that do not exist`() {
        val groupname = "testGroup"
        every { groupRepository.findByName(groupname) } throws GroupNotFoundException("")

        assertThrows(GroupNotFoundException::class.java) {
            groupApplication.getGroup(groupname)
        }
    }

    @Test
    fun `create group returns saved group`() {
        val groupname = "testGroup"
        every { groupRepository.findByName(groupname) } returns null
        every { groupRepository.save(any()) } returns UserGroup(1, groupname)

        val createdGroup = groupApplication.createGroup(groupname)

        Assertions.assertEquals(
            groupname, createdGroup
        )
    }

    @Test
    fun `create group throws error when creating group that already exists`() {
        val groupname = "testGroup"
        every { groupRepository.findByName(groupname) } throws GroupAlreadyExistsException("")

        assertThrows(GroupAlreadyExistsException::class.java) {
            groupApplication.createGroup(groupname)
        }
    }

    @Test
    fun `delete group returns deleted groups name`() {
        val groupname = "testGroup"
        every { groupRepository.findByName(groupname) } returns UserGroup(1, groupname)
        every { groupRepository.delete(any()) } returns Unit

        val deletedGroup = groupApplication.deleteGroup(groupname)

        Assertions.assertEquals(
            groupname, deletedGroup
        )
    }

    @Test
    fun `delete group throws error when removing group that do not exist`() {
        val groupname = "testGroup"
        every { groupRepository.findByName(groupname) } throws GroupNotFoundException("")

        assertThrows(GroupNotFoundException::class.java) {
            groupApplication.deleteGroup(groupname)
        }
    }

    @Test
    fun `get or create groups returns groups`() {
        val username1 = "testGroup1"
        val username2 = "testGroup2"
        val userGroup1 = UserGroup(1, username1)
        val userGroup2 = UserGroup(2, username2)
        every { groupRepository.findByName(userGroup1.name) } returns userGroup1
        every { groupRepository.findByName(userGroup2.name) } returns null
        every { groupRepository.save(any()) } returns userGroup2

        val groups = groupApplication.getOrCreateGroups(setOf(userGroup1.name, userGroup2.name))

        Assertions.assertEquals(
            setOf(userGroup1, userGroup2), groups
        )
    }
}
