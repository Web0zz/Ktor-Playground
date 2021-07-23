package com.web0zz.routing

import com.web0zz.auth.JwtConfig
import com.web0zz.model.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.db.DB
import org.kodein.db.find
import org.kodein.db.getById

fun Application.registerAuthRoutes(db: DB) {
    routing {
        authUser(db)
    }
}

fun Route.authUser(db: DB) {
    route("/auth") {
        post("/register") {
            val jwt = JwtConfig.instance

            val user = runCatching { call.receive<User>() }.getOrElse {
                return@post call.respondText(
                    "Missing or malformed user",
                    status = HttpStatusCode.BadRequest
                )
            }

            if(db.getById<User>(user.id) != null) {
                return@post call.respondText(
                    "User is not available",
                    status = HttpStatusCode.BadRequest
                )
            }

            db.put(user)
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

            if(db.getById<User>(user.id) == null) {
                return@post call.respondText(
                    "User is not available",
                    status = HttpStatusCode.BadRequest
                )
            }

            call.respond(jwt.sign(user.id))
        }
    }
}
