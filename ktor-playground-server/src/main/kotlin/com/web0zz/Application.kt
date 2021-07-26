package com.web0zz

import com.web0zz.handler.MainHandler
import com.web0zz.wsocket.chatSocket
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.websocket.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val handler = MainHandler()
    install(WebSockets)
    routing {
        chatSocket(handler)
    }
}
