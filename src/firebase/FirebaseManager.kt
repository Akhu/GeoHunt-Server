package com.pickle.punktual.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions


object FirebaseManager {

    var options: FirebaseOptions = FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.getApplicationDefault())
        .build()

}