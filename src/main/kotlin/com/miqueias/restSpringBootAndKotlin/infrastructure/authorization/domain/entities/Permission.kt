package com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import java.util.UUID

@Entity
@Table(name = "permission", schema = "security")
data class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val guid: UUID?,
    @Column(name = "description", nullable = false, length = 255)
    val description: String,
) : GrantedAuthority {
    override fun getAuthority() = description
}
