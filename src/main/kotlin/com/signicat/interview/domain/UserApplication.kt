package com.signicat.interview.domain

import com.signicat.interview.infrastructure.exception.UserAlreadyExistsException
import com.signicat.interview.infrastructure.exception.UserNotFoundException
import com.signicat.interview.infrastructure.exception.WrongPasswordException


class UserApplication internal constructor(
    private val subjectRepository: SubjectRepository,
    private val groupApplication: GroupApplication
) {

    fun registerUser(username: String, password: String): String {
        val groups = groupApplication.getOrCreateGroups(setOf("user", "admin"))
        val user = Subject(username = username, password = password, groups = groups)
        subjectRepository.findByUsername(user.username)
            ?.let { throw UserAlreadyExistsException("Username $username already exists!") }
        return subjectRepository.save(user).username
    }

    fun signInUser(username: String, password: String): String {
        val user = subjectRepository.findByUsername(username)
        checkUserCredentials(user, username, password)
        return "JWT_LOGGED_IN"
    }

    private fun checkUserCredentials(user: Subject?, username: String, password: String): Boolean {
        user?.username ?: throw UserNotFoundException("Username $username not found!")
        if (user.password != password) throw WrongPasswordException("Wrong password!") else return true
    }
}