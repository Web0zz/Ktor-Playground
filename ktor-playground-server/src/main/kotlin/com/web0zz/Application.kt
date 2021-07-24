package com.web0zz

import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import java.util.*
import kotlin.collections.LinkedHashSet

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(WebSockets)
    routing {
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
        webSocket("/chat") {
            println("Adding user!")
            val thisConnection = Connection(this)
            thisConnection.name = getUsername()
            connections += thisConnection
            try {
                send("You are connected! There are ${connections.count()} users here.")
                for(frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val textWithUsername = "[${thisConnection.name}]: $receivedText"
                    connections.forEach {
                        it.session.send(textWithUsername)
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Removing $thisConnection!")
                connections -= thisConnection
            }
        }
    }
}

suspend fun WebSocketSession.getUsername(): String {
    send("Enter Username: ")
    var username = "0"
    for(frame in incoming) {
        frame as? Frame.Text ?: continue
        username = frame.readText()
        break
    }
    return username
}