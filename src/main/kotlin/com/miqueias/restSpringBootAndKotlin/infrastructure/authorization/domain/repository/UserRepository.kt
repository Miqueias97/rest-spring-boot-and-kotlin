package com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.repository

import com.miqueias.restSpringBootAndKotlin.domain.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    @Query("select u from User u where u.name = :username")
    fun findByUsername(username: String): User?
}