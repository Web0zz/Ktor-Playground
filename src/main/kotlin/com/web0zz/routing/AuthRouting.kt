package com.web0zz.routing

import io.ktor.application.*
import io.ktor.routing.*

fun Application.registerAuthRoutes() {
    routing {
        authUser()
    }
}

fun Route.authUser() {
    route("/auth") {
        post("/register") {

        }

        post("/login") {

        }
    }
}
