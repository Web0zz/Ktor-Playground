package com.web0zz.routing

import com.web0zz.model.orderStorage
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.registerOrderRoutes() {
    routing {
        orderRoute()
    }
}

fun Route.orderRoute() {
    authenticate {
        get("/order") {
            call.principal<UserIdPrincipal>()
                ?: return@get call.respond("Access Denied")

            if (orderStorage.isNotEmpty()) {
                call.respond(orderStorage)
            } else call.respond("Not Found Any Data")
        }

        route("/order/") {

            get("{id}") {
               call.principal<UserIdPrincipal>()
                    ?: return@get call.respond("Access Denied")

                val id = call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
                val order = orderStorage.find { it.number == id } ?: return@get call.respondText(
                    "Not Found",
                    status = HttpStatusCode.NotFound
                )
                call.respond(order)
            }

            get("{id}/total") {
                call.principal<UserIdPrincipal>()
                    ?: return@get call.respond("Access Denied")

                val id = call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
                val order = orderStorage.find { it.number == id } ?: return@get call.respondText(
                    "Not Found",
                    status = HttpStatusCode.NotFound
                )
                val total = order.contents.map { it.price * it.amount }.sum()
                call.respond(total)
            }
        }
    }
}
