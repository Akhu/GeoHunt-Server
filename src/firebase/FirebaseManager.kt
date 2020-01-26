package com.pickle.punktual.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.pickle.punktual.position.LocationType
import com.pickle.punktual.user.User


class FirebaseManager {


    companion object {

        var options: FirebaseOptions = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .build()

        fun sendNotificationToTeam(
            userArriving: User,
            team: List<User>,
            type: LocationType
        ){
            when(type){
                LocationType.PAPETERIE -> {
                    dispatchNotification(team, userArriving, payload = mapOf(
                        "userId" to userArriving.id.toString(),
                        "username" to userArriving.username,
                        "location" to type.name,
                        "title" to "${userArriving.username} is coming 🛵",
                        "message" to "${userArriving.username} is in route to Papeterie !"
                    ))
                }
                LocationType.CAMPUS_NUMERIQUE -> {
                    dispatchNotification(team, userArriving, payload = mapOf(
                        "userId" to userArriving.id.toString(),
                        "username" to userArriving.username,
                        "location" to type.name,
                        "title" to "${userArriving.username} has checked-in ✅",
                        "message" to "${userArriving.username} is now at Campus Numerique !"
                    ))
                }
                else -> {
                    println("Unhandled position type received")
                }
            }


        }

        private fun dispatchNotification(
            team: List<User>,
            userArriving: User,
            payload: Map<String, String>
            ) {
            team.forEach { userToNotify ->
                if (userToNotify.id != userArriving.id) {
                    userToNotify.pushToken?.let { token ->
                        sendNotificationToDeviceWithPayload(
                            registrationToken = token, payload = payload
                        )
                    }
                }
            }
        }

        private fun sendNotificationToDeviceWithPayload(registrationToken: String, payload: Map<String, String>) : Boolean {
            // See documentation on defining a message payload.
            val notificationMessage: Message = Message.builder()
                .putAllData(payload)
                .setToken(registrationToken)
                .build()

            // Send a message to the device corresponding to the provided
            // registration token.
            // Send a message to the device corresponding to the provided
            // registration token.
            return try {
                val response = FirebaseMessaging.getInstance().send(notificationMessage)
                true
            } catch (exception: FirebaseMessagingException){
                //Log message here
                // exception.message
                false
            }
        }

        fun sendNotificationToDevice(registrationToken: String, message: String): Boolean {
            return sendNotificationToDeviceWithPayload(registrationToken, mapOf("message" to message))
        }
    }


}