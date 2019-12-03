package com.pickle.punktual.position

import com.pickle.punktual.user.UserStorage
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import org.apache.http.HttpStatus
import java.util.*

fun Routing.position() {
    post("/position/register") {
        try {
            val position = call.receive<Position>()
            call.request.queryParameters["userId"]?.let { userId ->
                UserStorage
                    .userList
                    .find {
                        it.id == UUID.fromString(userId)
                    }?.let {
                        it.addPosition(position)
                        call.respond(it)
                    }?: run {
                        call.respond(HttpStatusCode.NotFound, "User not exists")
                    }
            } ?: run {
                call.respond(HttpStatusCode.BadRequest, "Missing parameter(s)")
            }
        } catch (exception: Exception){
            call.respond(HttpStatusCode.BadRequest, exception)
        }
    }
}
