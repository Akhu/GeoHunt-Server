package com.pickle.punktual.user

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.mustache.MustacheContent
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post

fun Routing.user() {
    get("/userList") {
        call.respond(MustacheContent("index.hbs", mapOf("userList" to UserStorage.userList)))
    }

    post("/register"){
        try {
            val user = call.receive<User>()
            UserStorage.userList.add(user)
            //Gather username in body
            //Save it in user collection
            call.respond(user)
        } catch (exception: Exception){
            call.respond(HttpStatusCode.BadRequest, exception)
        }
    }

    post("/login") {
        try {
            UserStorage.findLogin(call.receive())?.let {
                call.respond(it)
            } ?: run {
                call.respond(HttpStatusCode.NotFound,"User not found")
            }
        } catch (exception: Exception){
            call.respond(HttpStatusCode.BadRequest, exception)
        }



    }
}
