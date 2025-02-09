package dev.ap5.mtdapi.gtfs.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RouteType(val value: Int) {
    @SerialName("0")
    TRAM(0),

    @SerialName("1")
    SUBWAY(1),

    @SerialName("2")
    RAIL(2),

    @SerialName("3")
    BUS(3),

    @SerialName("4")
    FERRY(4),

    @SerialName("5")
    CABLE_TRAM(5),

    @SerialName("6")
    AERIAL_LIFT(6),

    @SerialName("7")
    FUNICULAR(7),

    @SerialName("11")
    TROLLEYBUS(11),

    @SerialName("12")
    MONORAIL(12)
}