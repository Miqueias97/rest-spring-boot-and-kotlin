package com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

@Entity
@Table(name = "user", schema = "security")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val guid: UUID? = null,
    @Column(name = "user_name", length = 255, nullable = false)
    val name: String? = null,
    @Column(name = "full_name", length = 255)
    val fullName: String? = null,
    @Column(name = "password", nullable = false, length = 255)
    val psw: String? = null,
    @Column(name = "account_non_expired")
    val accountNonExpired: Boolean = true,
    @Column(name = "account_non_locked")
    val accountNonLocked: Boolean = true,
    @Column(name = "credentials_non_expired")
    val credentialsNonExpired: Boolean = true,
    @Column(name = "enabled")
    val enabled: Boolean = true,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_permission",
        schema = "security",
        joinColumns = [JoinColumn(name = "guid_user")],
        inverseJoinColumns = [JoinColumn(name = "guid_permission")],
    )
    var permissions: List<Permission>? = null,
) : UserDetails {
    val roles: List<String?>
        get() {
            val roles: MutableList<String?> = mutableListOf()
            for (role in permissions!!) {
                roles.add(role.description)
            }
            return roles
        }

    override fun getAuthorities() = permissions!!

    override fun getPassword() = psw!!

    override fun getUsername() = name!!
}
