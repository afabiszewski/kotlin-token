package com.signicat.interview.domain
import javax.persistence.*

@Entity
internal data class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val username: String,
    val password: String,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val groups: Set<UserGroup>
)
