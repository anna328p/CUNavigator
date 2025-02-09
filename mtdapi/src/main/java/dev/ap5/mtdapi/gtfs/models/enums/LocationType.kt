package dev.ap5.mtdapi.gtfs.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class LocationType(val value: Int) {
    @SerialName("0")
    STOP_PLATFORM(0),

    @SerialName("1")
    STATION(1),

    @SerialName("2")
    ENTRANCE_EXIT(2),

    @SerialName("3")
    GENERIC_NODE(3),

    @SerialName("4")
    BOARDING_AREA(4),

    STOP_PLATFORM_EMPTY(-1) // Representing empty string as a value
}