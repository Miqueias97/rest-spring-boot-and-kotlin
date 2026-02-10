package com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.service

import com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.dto.AccountCredential
import com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.dto.TokenDto
import com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AccessAuthorizationService(
    private val repository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: JwtTokenProvider,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun signIn(data: AccountCredential): TokenDto {
        try {
            logger.info("Attempting to sign in")
            val username = data.username
            val password = data.password

            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            val user = repository.findByUsername(data.username)
            val response =
                if (user != null) {
                    tokenProvider.createToken(username, user.roles)
                } else {
                    throw UsernameNotFoundException("User not found")
                }
            logger.info("Successfully signed in")
            return response
        } catch (ex: Exception) {
            logger.error("Erro na autenticação: ${ex.message}")
            throw UsernameNotFoundException("User ${data.username} not found", ex)
        }
    }
}