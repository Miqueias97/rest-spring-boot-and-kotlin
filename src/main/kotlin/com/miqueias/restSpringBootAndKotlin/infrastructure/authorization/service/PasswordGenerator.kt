package com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.service

import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class PasswordGenerator(
    private val passwordEncoder: PasswordEncoder,
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    fun generate() {
        logger.info("Generating password")
        val encoded = passwordEncoder.encode("teste")
        logger.debug("Senha VÁLIDA para o sistema: $encoded")
    }
}