package com.web0zz.routing

import com.web0zz.auth.JwtConfig
import com.web0zz.model.User
import com.web0zz.model.userStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.registerAuthRoutes() {
    routing {
        authUser()
    }
}

fun Route.authUser() {
    route("/auth") {
        post("/register") {
            val jwt = JwtConfig.instance

            val user = runCatching { call.receive<User>() }.getOrElse {
                return@post call.respondText(
                    "Missing or malformed user",
                    status = HttpStatusCode.BadRequest
                )
            }

            if(userStorage.contains(user)) {
                return@post call.respondText(
                    "User is not available",
                    status = HttpStatusCode.BadRequest
                )
            }
            
            userStorage.add(user)
            call.respond(jwt.sign(user.id))
        }

        post("/login") {
            val jwt = JwtConfig.instance

            val user = runCatching { call.receive<User>() }.getOrElse {
                return@post call.respondText(
                    "Missing or malformed user",
                    status = HttpStatusCode.BadRequest
                )
            }

            if(!userStorage.contains(user)) {
                return@post call.respondText(
                    "User is not available",
                    status = HttpStatusCode.BadRequest
                )
            }

            call.respond(jwt.sign(user.id))
        }
    }
}
