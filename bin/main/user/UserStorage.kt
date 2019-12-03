package com.pickle.punktual.user

object UserStorage {
    fun findLogin(attemptedUser: UserLogin): User? {
        return userList.find { (it.id == attemptedUser.id) && (it.username == attemptedUser.username) }
    }

    val userList = ArrayList<User>()
}