package dev.ap5.mtdapi.gtfs.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PickupDropOffType(val value: Int) {
    @SerialName("0")
    REGULARLY_SCHEDULED(0),

    @SerialName("1")
    NO_PICKUP_DROP_OFF(1),

    @SerialName("2")
    MUST_PHONE_AGENCY(2),

    @SerialName("3")
    MUST_COORDINATE_WITH_DRIVER(3),

    @SerialName("")
    REGULARLY_SCHEDULED_EMPTY(-1) // Representing empty string as default value
}