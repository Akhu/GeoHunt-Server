package com.pickle.punktual.position

import org.joda.time.DateTime
import javax.xml.stream.Location

enum class LocationType {
    PAPETERIE,
    CAMPUS_NUMERIQUE,
    UNKNOWN
}

data class Position(val latitude: Double, val longitude: Double)

data class PositionHistory(val date: DateTime,val position: Position, val type: LocationType)