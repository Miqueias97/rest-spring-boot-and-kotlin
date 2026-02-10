package com.miqueias.restSpringBootAndKotlin.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
}