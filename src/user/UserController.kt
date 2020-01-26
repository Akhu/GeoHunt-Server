package com.pickle.punktual.user

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.mustache.MustacheContent
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import kotlinx.coroutines.io.readUTF8Line

fun Routing.user() {
    get("/userList") {
        call.respond(MustacheContent("index.hbs", mapOf("userList" to UserStorage.userList)))
    }

    get("/team/all"){
        call.respond(UserStorage.userList)
    }

    post("/test"){
        val user = call.receive<UserTest>()
        call.respond(user)
    }

    post("/register"){
        try {
            val user = call.receive<User>()
            UserStorage.registerUser(user)?.let {
                call.respond(it)
            }?: run {
                call.respond(HttpStatusCode.Conflict, "User ${user.username} already exists, please log in instead")
            }
        } catch (exception: Exception){
            call.respond(HttpStatusCode.BadRequest, exception)
        }
    }

    post("/logout"){
        try {
            call.parameters["userId"]?.let {
                UserStorage.disconnectUser(it)
                call.respond(HttpStatusCode.OK, "disconnected")
            }?: run {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        } catch (exception: Exception){
            call.respond(HttpStatusCode.BadRequest, exception)
        }
    }


    post("/login") {
        try {
            val userAttemptedLogin:UserLogin = call.receive()
            UserStorage.findLogin(userAttemptedLogin).also { result ->
                if (!result.first) {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                }
                result.second?.let {
                    it.pushToken = userAttemptedLogin.pushToken
                    it.isConnected = true
                    call.respond(it)
                }
            }
        } catch (exception: Exception){
            call.respond(HttpStatusCode.BadRequest, exception)
        }
    }
}
