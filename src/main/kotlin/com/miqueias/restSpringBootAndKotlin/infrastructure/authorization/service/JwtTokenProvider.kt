package com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.miqueias.restSpringBootAndKotlin.config.EnvironmentVariables
import com.miqueias.restSpringBootAndKotlin.infrastructure.authorization.domain.dto.TokenDto
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.Base64
import java.util.Date

@Service
class JwtTokenProvider(
    private val env: EnvironmentVariables,
    private val userDetailsService: UserDetailsService,
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    val secretKey = Base64.getEncoder().encodeToString(env.secretKey.toByteArray())
    val algorithm = Algorithm.HMAC256(secretKey.toByteArray())

    fun createToken(
        username: String,
        roles: List<String?>,
    ): TokenDto {
        logger.info("Creating token")
        val now = Date()
        val validity = Date(now.time + env.expireLength)
        val accessToken = getAccessToken(username, roles, now, validity)
        val refreshToken = getRefreshToken(username, roles, now)

        return TokenDto(
            username = username,
            authenticated = true,
            created_at = now,
            expires_at = validity,
            access_token = accessToken,
            refresh_token = refreshToken,
        )
    }

    private fun getRefreshToken(
        username: String,
        roles: List<String?>,
        now: Date,
    ): String {
        logger.info("Getting refresh token")
        val validity = Date(now.time + env.expireLength * 3)
        return JWT
            .create()
            .withClaim("roles", roles)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .sign(algorithm)
            .trim()
    }

    private fun getAccessToken(
        username: String,
        roles: List<String?>,
        now: Date,
        validity: Date,
    ): String {
        val issuerURL = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString()
        return JWT
            .create()
            .withClaim("roles", roles)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .withIssuer(issuerURL)
            .sign(algorithm)
            .trim()
    }

    fun getAuthentication(token: String): Authentication {
        val decodedJWT: DecodedJWT = decodedToken(token)
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(decodedJWT.subject)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    private fun decodedToken(token: String): DecodedJWT {
        val algorithm = Algorithm.HMAC256(secretKey.toByteArray())
        val verifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }

    fun resolveToken(req: HttpServletRequest?): String? {
        val bearer = req?.getHeaders("Authorization").toString()

        return if (bearer.isNotBlank() && bearer.startsWith("Bearer ")) {
            bearer.substring("Bearer ".length)
        } else {
            null
        }
    }

    fun validateToken(token: String): Boolean {
        val decodedJWT: DecodedJWT = decodedToken(token)
        try {
            return !decodedJWT.expiresAt.before(Date())
        } catch (e: JWTVerificationException) {
            throw RuntimeException("Expired JWT token", e)
        }
    }
}