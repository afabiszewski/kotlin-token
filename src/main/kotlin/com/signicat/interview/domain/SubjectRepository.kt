package com.signicat.interview.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal interface SubjectRepository : JpaRepository<Subject, Int> {
}