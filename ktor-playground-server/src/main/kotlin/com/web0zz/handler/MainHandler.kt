package com.web0zz.handler

import com.web0zz.command.handler.CommandHandler
import com.web0zz.command.model.Command
import com.web0zz.model.Connection
import io.ktor.http.cio.websocket.*

object MainHandler: Handler {
    override fun getFrame(sender: Connection, connections: List<Connection>, frame: Frame) : Boolean =
        when(frame) {
            is Frame.Text -> { parseInput(sender, connections, frame) }
            else -> false
        }

    override fun parseInput(sender: Connection, connections: List<Connection>, text: Frame.Text) : Boolean {
        val receivedText = text.readText()
        val receivedTextList = receivedText.split(' ')

        if (receivedText.isEmpty()) return false

        if (receivedText.length < 5) {
            return commandIdentifier(sender, connections, listOf("/a", receivedText))
        }

        return when (receivedTextList.first()) {
            "/w" -> {
                val username = receivedTextList[1].dropLast(1)
                val message = receivedTextList.subList(2,receivedTextList.count()).joinToString(" ")
                commandIdentifier(sender, connections, listOf("/w", username, message))
            }
            "/g" -> {
                val groupname = receivedTextList[1].dropLast(1)
                val message = receivedTextList.subList(2,receivedTextList.count()).joinToString(" ")
                commandIdentifier(sender, connections, listOf("/g", groupname, message))
            }
            else -> commandIdentifier(sender, connections, listOf("/a", receivedText))
        }
    }

    override fun commandIdentifier(sender: Connection, connections: List<Connection>, input: List<String>) : Boolean {
        when(input.first()) {
            "/w" -> {
                val receiver = connections.find { it.name == input[1] } ?: return false
                callCommand(Command.Whisper(sender, receiver, input[2]))
            }
            "/g" -> {
                val receiver = connections.filter { it.group == input[1] }
                if (receiver.isNullOrEmpty()) return false
                callCommand(Command.ToGroup(sender, receiver, input[2]))
            }
            "/a" -> {
                callCommand(Command.ToAll(sender, connections, input[1]))
            }
            else -> return false
        }
        return false
    }

    override fun callCommand(command: Command) : Boolean {
        return CommandHandler(command).invokeCommend()
    }
}