package com.web0zz.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

open class JwtConfig private constructor(secret: String) {
    private val algorithm = Algorithm.HMAC256(secret)
    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withAudience(AUDIENCE)
        .withIssuer(ISSUER)
        .build()

    fun sign(userId: String): String = JWT
        .create()
        .withAudience(AUDIENCE)
        .withIssuer(ISSUER)
        .withClaim(CLAIM, userId)
        .sign(algorithm)

    companion object {
        lateinit var instance: JwtConfig
            private set

        fun initialize(secret: String) {
            synchronized(this) {
                if (!this::instance.isInitialized) {
                    instance = JwtConfig(secret)
                }
            }
        }

        private const val ISSUER = "http://0.0.0.0:8080"
        private const val AUDIENCE = "http://0.0.0.0:8080"
        const val CLAIM = "userId"
    }
}