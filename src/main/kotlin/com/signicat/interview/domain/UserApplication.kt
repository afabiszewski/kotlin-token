package com.signicat.interview.domain

import com.signicat.interview.infrastructure.exceptions.UserAlreadyExistsException
import com.signicat.interview.infrastructure.exceptions.UserNotFoundException

class UserApplication internal constructor(
    private val subjectRepository: SubjectRepository,
    private val groupRepository: GroupRepository
) {

    fun registerUser(name: String, password: String): String {
        val userGroup = UserGroup(name = "users")
        val user = Subject(username = name, password = password, groups = setOf(userGroup))
        subjectRepository.findFirstByUsername(user.username)
            ?.let { throw UserAlreadyExistsException("Username $name already exists!") }
        return subjectRepository.save(user).username
    }

    fun signInUser(name: String, password: String): String {
        return subjectRepository.findFirstByUsername(name)?.username
            ?: throw UserNotFoundException("Username $name not found!")
    }
}