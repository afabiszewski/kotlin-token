package com.signicat.interview.domain

import com.signicat.interview.infrastructure.exception.UserAlreadyExistsException
import com.signicat.interview.infrastructure.exception.UserNotFoundException
import com.signicat.interview.infrastructure.exception.WrongPasswordException
import com.signicat.interview.security.AuthorizationService


class UserApplication internal constructor(
    private val subjectRepository: SubjectRepository,
    private val groupApplication: GroupApplication,
    private val authorizationService: AuthorizationService
) {

    fun registerUser(username: String, password: String, groups: List<String>): String {
        val userGroups = groupApplication.getOrCreateGroups(groups.toSet())
        val user = Subject(username = username, password = password, groups = userGroups)
        subjectRepository.findByUsername(user.username)
            ?.let { throw UserAlreadyExistsException("Username $username already exists!") }
        return subjectRepository.save(user).username
    }

    fun signInUser(username: String, password: String): String {
        val user =
            subjectRepository.findByUsername(username) ?: throw UserNotFoundException("Username $username not found!")
        if (user.password != password) throw WrongPasswordException("Wrong password!")
        val groupsMap = HashMap<Long, String>()
        user.groups.forEach { userGroup -> groupsMap[userGroup.id] = userGroup.name }
        return authorizationService.generateTokenForUser(user.id, username, groupsMap)
    }
}