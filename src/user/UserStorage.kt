package com.pickle.punktual.user

object UserStorage {
    fun findLogin(attemptedUser: UserLogin): User? {
        return userList.find { (it.id == attemptedUser.id) && (it.username == attemptedUser.username) }
    }


    fun registerUser(user: User) : Boolean {
        if(userList.contains(user)){
            return false
        }

        userList.add(user)
        return true
    }

    val userList = ArrayList<User>()
}