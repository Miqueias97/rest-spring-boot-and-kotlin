package com.miqueias.restSpringBootAndKotlin.infrastructure.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.Date

@RestControllerAdvice
class CustomizedExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun genericHandler(
        ex: Exception,
        req: WebRequest,
    ): ResponseEntity<ExceptionResponse> {
        val response =
            ExceptionResponse(
                timestamp = Date(),
                message = ex.message,
                details = req.getDescription(false),
            )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(e: AccessDeniedException, req: WebRequest,): ResponseEntity<String> {
        return ResponseEntity(e.message ?: "Access Denied", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUserNotFound(e: UsernameNotFoundException): ResponseEntity<String> {
        return ResponseEntity(e.message ?: "User not found", HttpStatus.UNAUTHORIZED)
    }
}
