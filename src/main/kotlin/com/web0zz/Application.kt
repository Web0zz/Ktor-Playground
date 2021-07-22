package com.web0zz

import com.web0zz.auth.JwtConfig
import com.web0zz.model.userStorage
import com.web0zz.routing.registerAuthRoutes
import com.web0zz.routing.registerOrderRoutes
import com.web0zz.routing.registerUserRoutes
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.serialization.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    with(ConfigUtil(environment.config)) {
        JwtConfig.initialize(SECRET_KEY)
    }

    install(Authentication) {
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JwtConfig.CLAIM).asString()
                if(userStorage.any { user -> user.id == claim }) {
                    UserIdPrincipal(claim)
                } else null
            }
        }
    }

    install(ContentNegotiation) {
        json()
    }
    registerAuthRoutes()
    registerUserRoutes()
    registerOrderRoutes()
}