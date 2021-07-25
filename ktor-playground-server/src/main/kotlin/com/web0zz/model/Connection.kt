package com.web0zz.model

import io.ktor.http.cio.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class Connection(val session: DefaultWebSocketSession) {
    companion object {
        var lastId = AtomicInteger(0)
    }
    var name = "user${lastId.getAndIncrement()}"
    var group = ""
}