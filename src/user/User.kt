package com.pickle.punktual.user

import com.fasterxml.jackson.annotation.JsonAlias
import com.pickle.punktual.position.LocationType
import com.pickle.punktual.position.Position
import com.pickle.punktual.position.PositionHistory
import org.joda.time.DateTime
import java.util.*
import kotlin.collections.ArrayList


enum class UserType {
    STUDENT, TEACHER
}
data class UserTest(
    val username: String,
    var pushToken: String? = null
)
data class User(
    var id: UUID = UUID.randomUUID(),
    val type: UserType = UserType.STUDENT,
    override val username: String,
    override var pushToken: String? = null,
    val imageUrl: String? = null) : UserBasic {
    var isConnected = false
    //Joda time used : https://www.joda.org/joda-time/quickstart.html
    //Each user will get a position list, but we remember only a few of it's declared positions
    private var positionList: ArrayList<PositionHistory> = ArrayList()

    val positionHistory : List<PositionHistory>
        get() = positionList

    fun addPosition(position: Position, type: LocationType){
        if(positionList.size >= 5){
            positionList = positionList.dropLast(1) as ArrayList<PositionHistory>
        }
        positionList.add(PositionHistory(DateTime.now(), position, type))
    }
}

data class UserLogin(override var pushToken: String?, override val username: String) : UserBasic

interface UserBasic {
    val username: String
    var pushToken: String?
}
