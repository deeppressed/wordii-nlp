package io.wordii.nlp.server

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import io.wordii.nlp.client.ClientNlpParser

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        accept(ContentType.Text.Plain) {
            post("/nlp/depparse") {
                val content = call.receiveText()
                val result = ClientNlpParser.depParse(content)
                call.respond(HttpStatusCode.OK, result)
            }
        }
    }
}
