package com.web0zz.wsocket

import com.web0zz.handler.MainHandler
import com.web0zz.model.Connection
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import java.util.*
import kotlin.collections.LinkedHashSet

fun Routing.chatSocket() {
    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
    val handler = MainHandler

    webSocket("/chat") {
        println("Adding user!")

        val thisConnection = Connection(this)
        val userInfo = getUsername()
        thisConnection.name = userInfo.first()
        thisConnection.group = userInfo[1]
        connections += thisConnection
        send("Welcome, my chat ${thisConnection.name} (${thisConnection.group}")

        /**
        *  How to use chat?
        *  to send a private message to someone type: "/w username: your message"
        *  to send a private message to a group type: "/g GroupName: your message"
        *  to send a public message to everyone just type your message
        */

        try {
            send("You are connected! There are ${connections.count()} users here.")
            for(frame in incoming) {
                frame as? Frame.Text ?: continue
                try {
                    handler.getFrame(thisConnection,connections,frame)
                } catch (e: Exception) {
                    println(e.localizedMessage)
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

suspend fun WebSocketSession.getUsername(): List<String>{
    send("Enter Username and Group (Username Group): ")
    var username = "0"
    var group = "1"
    for(frame in incoming) {
        frame as? Frame.Text ?: continue
        val input = frame.readText().split(" ")
        username = input.first()
        group = input[1]
        break
    }
    return listOf(username,group)
}