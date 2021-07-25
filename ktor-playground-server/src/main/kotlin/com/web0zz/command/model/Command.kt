package com.web0zz.command.model

import com.web0zz.model.Connection

/**
 * Identifier for [Command] type.
 *
 * This class will be passed from [com.web0zz.handler.Handler] to [com.web0zz.command.handler.CommandHandler],
 * where It will be used to invoke command properly.
 */
sealed class Command {
    /**
     * Command types.
     *
     * @param sender: Sender's User information
     * @param receiver: User information of the person(s) who will receive the data
     * @param data: The data to be sent to the user(s)
     */

    // Send data to certain user
    class Whisper(val sender: Connection, val receiver: Connection, val data: String) : Command()

    // Send data to user groups
    class ToGroup(val sender: Connection, val receiver: List<Connection>, val data: String) : Command()

    // Send data to all user
    class ToAll(val sender: Connection, val receiver: List<Connection>, val data: String) : Command()
}
