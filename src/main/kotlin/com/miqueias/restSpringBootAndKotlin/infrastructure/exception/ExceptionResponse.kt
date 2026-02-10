package com.miqueias.restSpringBootAndKotlin.infrastructure.exception

import java.util.Date

data class ExceptionResponse(
    val timestamp: Date = Date(),
    val message: String?,
    val details: String?,
)
