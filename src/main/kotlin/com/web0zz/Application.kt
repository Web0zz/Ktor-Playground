package com.web0zz

import com.web0zz.auth.JwtConfig
import com.web0zz.model.Order
import com.web0zz.model.User
import com.web0zz.routing.registerAuthRoutes
import com.web0zz.routing.registerOrderRoutes
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.serialization.*
import org.kodein.db.*
import org.kodein.db.impl.open
import org.kodein.db.orm.kotlinx.KotlinxSerializer


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    val db = DB.open("playground_db",
            TypeTable {
                root<User>()
                root<Order>()
            }, KotlinxSerializer()
        )

    with(ConfigUtil(environment.config)) {
        JwtConfig.initialize(SECRET_KEY)
    }

    install(Authentication) {
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JwtConfig.CLAIM).asString()
                if(db.getById<User>(claim) != null) {
                    UserIdPrincipal(claim)
                } else null
            }
        }
    }

    install(ContentNegotiation) {
        json()
    }
    registerAuthRoutes(db)
    registerOrderRoutes(db)
}