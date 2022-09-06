package com.afabiszewski.token.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal interface GroupRepository : JpaRepository<UserGroup, Int> {

    fun findByName(name: String): UserGroup?
}