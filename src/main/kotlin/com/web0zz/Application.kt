package com.web0zz

import com.web0zz.model.BlogEntry
import com.web0zz.model.blogData
import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "docs")
        outputFormat = HTMLOutputFormat.INSTANCE
    }
    routing {
        get("/") {
            call.respond(FreeMarkerContent("portfolio.ftl", mapOf("entries" to blogData), ""))
        }
        post("/submit") {
            val params = call.receiveParameters()
            val headline = params["headline"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val body = params["body"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val newEntry = BlogEntry(headline, body)
            blogData.add(0, newEntry)
            call.respondHtml {
                body {
                    h1 {
                        +"Thanks for submitting your entry!"
                    }
                    p {
                        +"We've submitted your new entry titled "
                        b {
                            +newEntry.headline
                        }
                    }
                    p {
                        +"You have submitted a total of ${blogData.count()} articles!"
                    }
                    a("/") {
                        +"Go back"
                    }
                }
            }
        }
    }
}
