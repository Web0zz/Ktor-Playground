package com.web0zz.routing

import com.web0zz.model.Order
import com.web0zz.model.User
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.db.DB
import org.kodein.db.find
import org.kodein.db.getById
import org.kodein.db.useModels

fun Application.registerOrderRoutes(db: DB) {
    routing {
        orderRoute(db)
    }
}

fun Route.orderRoute(db: DB) {
    authenticate {
        get("/order") {
            call.principal<UserIdPrincipal>()
                ?: return@get call.respond("Access Denied")

            val orders = db.find<Order>().all().useModels { it.toList() }
            call.respond(orders)
        }

        route("/order/") {
            post("new") {
                val order = runCatching { call.receive<Order>() }.getOrElse {
                    return@post call.respondText(
                        "Couldn't resolve the Order",
                        status = HttpStatusCode.BadRequest
                    )
                }

                if(db.getById<Order>(order.id) != null) {
                    return@post call.respondText(
                        "Order is already made it before",
                        status = HttpStatusCode.BadRequest
                    )
                }

                db.put(order)
                call.respond("Order received")
            }

            get("{id}") {
               call.principal<UserIdPrincipal>()
                    ?: return@get call.respond("Access Denied")

                val id = call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
                val order = db.getById<Order>(id) ?: return@get call.respondText(
                    "Not Found",
                    status = HttpStatusCode.NotFound
                )
                call.respond(order)
            }

            get("{id}/total") {
                call.principal<UserIdPrincipal>()
                    ?: return@get call.respond("Access Denied")

                val id = call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
                val order = db.getById<Order>(id) ?: return@get call.respondText(
                    "Not Found",
                    status = HttpStatusCode.NotFound
                )
                val total = order.price.toInt() * order.amount.toInt()
                call.respond(total)
            }
        }
    }
}
