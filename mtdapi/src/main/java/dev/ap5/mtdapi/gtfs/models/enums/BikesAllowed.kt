package dev.ap5.mtdapi.gtfs.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class BikesAllowed(val value: Int) {
    @SerialName("0")
    NO_INFO(0),

    @SerialName("1")
    BIKES_ALLOWED(1),

    @SerialName("2")
    BIKES_NOT_ALLOWED(2)
}