package com.signicat.interview.domain

import com.signicat.interview.infrastructure.exceptions.UserAlreadyExistsException
import com.signicat.interview.infrastructure.exceptions.UserNotFoundException
import com.signicat.interview.infrastructure.exceptions.WrongPasswordException

class UserApplication internal constructor(
    private val subjectRepository: SubjectRepository
) {

    fun registerUser(username: String, password: String): String {
        val userGroup = UserGroup(name = "users")
        val user = Subject(username = username, password = password, groups = setOf(userGroup))
        subjectRepository.findFirstByUsername(user.username)
            ?.let { throw UserAlreadyExistsException("Username $username already exists!") }
        return subjectRepository.save(user).username
    }

    fun signInUser(username: String, password: String): String {
        val user = subjectRepository.findFirstByUsername(username)
        user?.username ?: throw UserNotFoundException("Username $username not found!")
        if (user.password != password) throw WrongPasswordException("Wrong password!")
        return "LOGED IN"
    }
}