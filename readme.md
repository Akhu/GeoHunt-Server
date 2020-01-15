# Punktual project for Campus

## Before starting the project
### FCM Admin SDK

Please push the env variable `GOOGLE_APPLICATION_CREDENTIALS` as key and as value the URI of the google credentials json file for your Firebase project (Service account)

see https://firebase.google.com/docs/cloud-messaging/server#firebase-admin-sdk-for-fcm for more informations

### ...

## Todos: 
1. Handle login
2. Ask for location permissions
3. Handle location settings
4. Create geofence
5. Start to listen for location changes
6. Create Notification when entering the fence
6. Implement google map to better see things
7. Put marker on Map for the papeterie and current user
8. Communicate with server to log arrival date time + user
9. Handle external notification from FCM
10. Improvments : Use Retrofit instead of plain OkHttp
11. Improvments : Refactoring the app using live data, navigation graph, etc.
12. Improvments : Google Login with profile picture
13. Improvments : RecyclerView to display all user of campus with their picture from Server
14. Improvments : Some statistics with Android Chart
15. Improvments : Detecting the type of transport user for a user + send to server

Link with a server that is able to send notification to other registered devices/


http://d3cima.tech:8080/userList