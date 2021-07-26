package com.web0zz.command.handler

import com.web0zz.command.model.Command
import io.ktor.http.cio.websocket.*

class CommandHandler(private val command: Command) {
    suspend fun invokeCommend() {
        when(command) {
            is Command.Whisper -> whisper(command)
            is Command.ToGroup -> toGroup(command)
            is Command.ToAll -> toAll(command)
        }
    }

    private suspend fun whisper(command: Command.Whisper) {
        val textWithUsername = "[${command.sender.name}]: ${command.data}"

        command.sender.session.send(textWithUsername)
        command.receiver.session.send(textWithUsername)
    }

    private suspend fun toGroup(command: Command.ToGroup) {
        val textWithUsername = "[${command.sender.name}]: ${command.data}"

        command.sender.session.send(textWithUsername)
        command.receiver.forEach {
            it.session.send(textWithUsername)
        }
    }

    private suspend fun toAll(command: Command.ToAll) {
        val textWithUsername = "[${command.sender.name}]: ${command.data}"

        command.sender.session.send(textWithUsername)
        command.receiver.forEach {
            it.session.send(textWithUsername)
        }
    }
}