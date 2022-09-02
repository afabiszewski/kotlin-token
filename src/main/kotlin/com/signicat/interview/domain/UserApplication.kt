package com.signicat.interview.domain

import kotlin.random.Random

class UserApplication internal constructor(private val subjectRepository: SubjectRepository) {

    fun createUser(name: String, password: String) : String {
        val userGroup = UserGroup(Random.nextInt(), "users")

        val user = Subject(Random.nextInt(), name, password, setOf(userGroup))
        return name
    }
}