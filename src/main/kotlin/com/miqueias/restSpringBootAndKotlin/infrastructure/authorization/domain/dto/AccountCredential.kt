package com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.dto

data class AccountCredential(
    val username: String,
    val password: String,
)
