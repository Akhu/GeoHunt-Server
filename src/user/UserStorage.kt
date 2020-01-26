package com.pickle.punktual.user

import java.util.*

object UserStorage {

    private fun findUser(user: UserBasic): Pair<Boolean, User?> {
        userList.find { ( it.username.toLowerCase() == user.username.toLowerCase() ) }?.let {
            return Pair(true, it)
        }
        return Pair(false, null)
    }

    fun findLogin(attemptedUser: UserLogin): Pair<Boolean, User?> = findUser(attemptedUser)


    fun registerUser(user: User) : User? {

        val result = findUser(user)
        if (result.first){
            //User already exists bye.
            return null
        }

        userList.add(user)
        return user
    }

    fun disconnectUser(userId: String) {
        userList.removeIf { it.id.toString() == userId }
    }

    val userList = mutableListOf<User>()
}