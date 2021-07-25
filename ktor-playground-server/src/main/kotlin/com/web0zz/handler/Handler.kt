package com.web0zz.handler

import com.web0zz.command.model.Command
import com.web0zz.model.Connection
import io.ktor.http.cio.websocket.*

interface Handler {
    /**
     * It will get frame and invoke [commandIdentifier]
     *
     * If frame not valid it will return false
     */
    fun getFrame(sender: Connection, connections: List<Connection>, frame: Frame) : Boolean

    /**
     * It will parse frame text and invoke [commandIdentifier]
     *
     * If parsed text not correct return false
     */
    fun parseInput(sender: Connection, connections: List<Connection>, text: Frame.Text) : Boolean

    /**
     * It will Identify parsed input and invoke [callCommand]
     *
     * If command not valid it will return false
     */
    fun commandIdentifier(sender: Connection, connections: List<Connection>, input: List<String>) : Boolean

    /**
     * Calls command to invoke
     *
     * If command failed it will return false
     */
    fun callCommand(command: Command) : Boolean
}