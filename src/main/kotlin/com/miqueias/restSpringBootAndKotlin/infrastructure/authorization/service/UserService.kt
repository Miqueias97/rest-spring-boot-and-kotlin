package com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.service

import com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun loadUserByUsername(username: String): UserDetails {
        logger.info("Loading user by username: $username")
        return userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")
    }
}