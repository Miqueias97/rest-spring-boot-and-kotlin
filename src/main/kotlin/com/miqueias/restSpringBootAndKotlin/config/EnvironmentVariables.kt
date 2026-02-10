package com.miqueias.restSpringBootAndKotlin.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class EnvironmentVariables {
    @Value("\${security.jwt.token.secret-key}")
    var secretKey: String = ""

    @Value("\${security.jwt.token.expire-length}")
    var expireLength: Long = 0L
}
