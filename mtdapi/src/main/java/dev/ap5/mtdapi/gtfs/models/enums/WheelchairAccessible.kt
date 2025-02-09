package dev.ap5.mtdapi.gtfs.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class WheelchairAccessible(val value: Int) {
    @SerialName("0")
    NO_INFO(0),

    @SerialName("1")
    ACCESSIBLE(1),

    @SerialName("2")
    NOT_ACCESSIBLE(2)
}