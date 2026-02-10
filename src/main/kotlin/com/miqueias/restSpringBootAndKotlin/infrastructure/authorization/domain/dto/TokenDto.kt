package com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.dto

import java.util.Date

data class TokenDto(
    val username: String,
    val authenticated: Boolean,
    val created_at: Date,
    val expires_at: Date,
    val access_token: String,
    val refresh_token: String,
)
