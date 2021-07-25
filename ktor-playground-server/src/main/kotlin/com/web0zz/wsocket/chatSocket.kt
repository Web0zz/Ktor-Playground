package com.web0zz.wsocket

import com.web0zz.model.Connection
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import java.util.*
import kotlin.collections.LinkedHashSet

fun Routing.chatSocket() {
    webSocket("/chat") {
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
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