package com.pickle.punktual

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.github.mustachejava.DefaultMustacheFactory
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import java.time.*
import com.pickle.punktual.firebase.FirebaseManager
import com.pickle.punktual.position.position
import com.pickle.punktual.user.user
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.mustache.Mustache
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.websocket.webSocket
import org.slf4j.event.Level
import java.time.Duration


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Mustache) {
        mustacheFactory = DefaultMustacheFactory("templates/mustache")
    }

    install(io.ktor.websocket.WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)

            registerModule(JodaModule())
        }
    }

    FirebaseApp.initializeApp(FirebaseManager.options)

    install(CallLogging){
        level = Level.INFO
    }

    log.info(FirebaseApp.getInstance().name)

    val client = HttpClient(CIO) {
    }

    routing {
        user()
        position()
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/notificationFCM"){

            var registrationToken = "ef3bGfoE1BE:APA91bHYI-5V9Ehzp5mUKSd74B89TX9bbXT06JwOejsLXGU5CM4MrxmCWWU88LwlVrQ2T4pk1V2s0mOz8FLsyGILcRlekQN0CuJngQ2Cl9ce3XsDGyKhDqyS55UCSw-EJbmmAV_Uj5JW"

            call.request.queryParameters["token"]?.let {
                registrationToken = it
            }

            val response = FirebaseManager.sendNotificationToDevice(registrationToken, "Hello World")
            // Response is a message ID string.
            // Response is a message ID string.
            call.respondText("Successfully sent message: $response")
        }


        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        webSocket("/myws/echo") {
            send(Frame.Text("Hi from server"))
            while (true) {
                val frame = incoming.receive()
                if (frame is Frame.Text) {
                    send(Frame.Text("Client said: " + frame.readText()))
                }
            }
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}


