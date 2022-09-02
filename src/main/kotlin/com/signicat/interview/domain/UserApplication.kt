package com.signicat.interview.domain

class UserApplication internal constructor(private val subjectRepository: SubjectRepository, private val groupRepository: GroupRepository) {

    fun registerUser(name: String, password: String) : String {
        val userGroup = UserGroup(name = "users")
        val user = Subject(username = name, password = password, groups = setOf(userGroup))

        return subjectRepository.save(user).username
    }
}