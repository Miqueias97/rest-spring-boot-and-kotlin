package com.miqueias.restSpringBootAndKotlin.application.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TesteController {

    @GetMapping
    fun test() = ResponseEntity.ok("Hello Spring Boot!")
}