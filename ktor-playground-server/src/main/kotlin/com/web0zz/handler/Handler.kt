package com.web0zz.handler

import com.web0zz.command.model.Command
import com.web0zz.model.Connection
import io.ktor.http.cio.websocket.*

interface Handler {
    /**
     * It will get frame and invoke [parseInput]
     */
    suspend fun getFrame(sender: Connection, connections: MutableSet<Connection>, frame: Frame.Text)

    /**
     * It will parse frame text and invoke [commandIdentifier]
     *
     * If parsed text not correct throw exception
     */
    suspend fun parseInput(sender: Connection, connections: MutableSet<Connection>, text: Frame.Text)
    /**
     * It will Identify parsed input and invoke [callCommand]
     *
     * If command not valid it will throw exception
     */
    suspend fun commandIdentifier(sender: Connection, connections: MutableSet<Connection>, input: List<String>)

    /**
     * Calls command to invoke
     */
    suspend fun callCommand(command: Command)
}