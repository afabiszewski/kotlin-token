package com.afabiszewski.token.domain

import com.afabiszewski.token.infrastructure.exception.UserAlreadyExistsException
import com.afabiszewski.token.infrastructure.exception.UserNotFoundException
import com.afabiszewski.token.infrastructure.exception.WrongPasswordException
import com.afabiszewski.token.security.AuthorizationService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserApplicationTest {
    private val groupRepository = mockk<GroupRepository>()
    private val subjectRepository = mockk<SubjectRepository>()
    private val authorizationService = mockk<AuthorizationService>()
    private val groupApplication = GroupApplication(groupRepository)
    private val userApplication =
        UserApplication(
            subjectRepository = subjectRepository,
            groupApplication = groupApplication,
            authorizationService = authorizationService
        )

    @Test
    fun `register user returns saved user`() {
        val username = "testUsername"
        val password = "testPassword"
        val groups = listOf("testGroup", "admin")
        val testGroup = UserGroup(1, "testGroup")
        val adminGroup = UserGroup(1, "adminGroup")
        every { groupRepository.findByName("testGroup") } returns testGroup
        every { groupRepository.findByName("admin") } returns null
        every { groupRepository.save(any()) } returns UserGroup(2, "admin")
        every { subjectRepository.findByUsername(username) } returns null
        every { subjectRepository.save(any()) } returns Subject(
            username = username,
            password = password,
            groups = setOf(testGroup, adminGroup)
        )

        val createdUser = userApplication.registerUser(username, password, groups)

        Assertions.assertEquals(
            username, createdUser
        )
    }

    @Test
    fun `create user throws error when creating user that already exists`() {
        val username = "testUsername"
        val password = "testPassword"
        val groups = listOf("testGroup", "admin")
        val testGroup = UserGroup(1, "testGroup")
        val adminGroup = UserGroup(1, "adminGroup")
        every { groupRepository.findByName("testGroup") } returns testGroup
        every { groupRepository.findByName("admin") } returns null
        every { groupRepository.save(any()) } returns UserGroup(2, "admin")
        every { subjectRepository.findByUsername(username) } returns Subject(
            username = username,
            password = password,
            groups = setOf(testGroup, adminGroup)
        )

        Assertions.assertThrows(UserAlreadyExistsException::class.java) {
            userApplication.registerUser(username, password, groups)
        }
    }

    @Test
    fun `sign in user returns token`() {
        val username = "testUsername"
        val password = "testPassword"
        val testGroup = UserGroup(1, "testGroup")
        val adminGroup = UserGroup(1, "testGroup2")
        every { groupRepository.findByName("testGroup") } returns testGroup
        every { groupRepository.findByName("testGroup2") } returns null
        every { groupRepository.save(any()) } returns UserGroup(2, "admin")
        every { subjectRepository.findByUsername(username) } returns Subject(
            username = username, password = password, groups = setOf(testGroup, adminGroup)
        )
        every { authorizationService.generateTokenForUser(any(), any(), any()) } returns "JWT"

        val token = userApplication.signInUser(username, password)

        Assertions.assertEquals(
            "JWT", token
        )
    }

    @Test
    fun `sign in user throws error when password is wrong`() {
        val username = "testUsername"
        val password = "rightPassword"
        val testGroup = UserGroup(1, "testGroup")
        val adminGroup = UserGroup(1, "adminGroup")
        every { subjectRepository.findByUsername(username) } returns Subject(
            username = username,
            password = "wrongPassword",
            groups = setOf(testGroup, adminGroup)
        )

        Assertions.assertThrows(WrongPasswordException::class.java) {
            userApplication.signInUser(username, password)
        }
    }

    @Test
    fun `sign in user throws error when signing in user that does not exist`() {
        val username = "testUsername"
        val password = "testPassword"
        every { subjectRepository.findByUsername(username) } returns null

        Assertions.assertThrows(UserNotFoundException::class.java) {
            userApplication.signInUser(username, password)
        }
    }
}