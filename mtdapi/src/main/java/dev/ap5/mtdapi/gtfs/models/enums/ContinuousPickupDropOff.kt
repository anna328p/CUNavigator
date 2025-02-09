package dev.ap5.mtdapi.gtfs.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ContinuousPickupDropOff(val value: Int) {
    @SerialName("0")
    CONTINUOUS_STOPPING_PICKUP_DROP_OFF(0),

    @SerialName("1")
    NO_CONTINUOUS_STOPPING_PICKUP_DROP_OFF(1),

    @SerialName("2")
    MUST_PHONE_AGENCY_TO_ARRANGE(2),

    @SerialName("3")
    MUST_COORDINATE_WITH_DRIVER(3)
}