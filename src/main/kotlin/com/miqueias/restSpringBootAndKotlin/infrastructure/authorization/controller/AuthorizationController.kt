package com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.controller

import com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.dto.AccountCredential
import com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.dto.TokenDto
import com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.service.AccessAuthorizationService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthorizationController(
    private val authorizationService: AccessAuthorizationService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/sign-in")
    fun singIn(@RequestBody body: AccountCredential): ResponseEntity<TokenDto> {
        try {
            logger.info("Authorizing authorization request")
            val response  = authorizationService.signIn(body)
            return ResponseEntity.ok(response)
        } catch (e: AccessDeniedException) {
            throw UsernameNotFoundException("Access Denied Exception", e)
        }
    }

}