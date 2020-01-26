package com.pickle.punktual.position

import com.fasterxml.jackson.annotation.JsonFormat
import org.joda.time.DateTime
import javax.xml.stream.Location

enum class LocationType {
    PAPETERIE,
    CAMPUS_NUMERIQUE,
    UNKNOWN
}

data class Position(val latitude: Double, val longitude: Double)

data class PositionHistory(
    @JsonFormat
        (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    val date: DateTime,
    val position: Position,
    val type: LocationType)