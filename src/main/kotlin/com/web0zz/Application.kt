package com.web0zz

import com.web0zz.routing.registerOrderRoutes
import com.web0zz.routing.registerUserRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        json()
    }
    registerUserRoutes()
    registerOrderRoutes()
}