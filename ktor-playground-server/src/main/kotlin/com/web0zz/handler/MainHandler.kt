package com.web0zz.handler

import com.web0zz.command.handler.CommandHandler
import com.web0zz.command.model.Command
import com.web0zz.model.Connection
import io.ktor.http.cio.websocket.*

object MainHandler : Handler {

    override suspend fun getFrame(sender: Connection, connections: MutableSet<Connection>, frame: Frame.Text) =
        parseInput(sender, connections, frame)

    override suspend fun parseInput(sender: Connection, connections: MutableSet<Connection>, text: Frame.Text) {
        val receivedText = text.readText()
        val receivedTextList = receivedText.split(' ')

        if (receivedText.isEmpty()) throw (Exception("Text empty"))

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

    override suspend fun commandIdentifier(sender: Connection, connections: MutableSet<Connection>, input: List<String>) {
        when(input.first()) {
            "/w" -> {
                val receiver = connections.find { it.name == input[1] } ?: throw (Exception("/w name find is null ${input[1]} not match"))
                callCommand(Command.Whisper(sender, receiver, input[2]))
            }
            "/g" -> {
                val receiver = connections.filter { it.group == input[1] }
                if (receiver.isEmpty()) throw (Exception("/g group list empty"))
                callCommand(Command.ToGroup(sender, receiver, input[2]))
            }
            "/a" -> {
                callCommand(Command.ToAll(sender, connections, input[1]))
            }
            else -> throw (Exception("commandIdentifier list error"))
        }
    }

    override suspend fun callCommand(command: Command) =
        CommandHandler(command).invokeCommend()
}