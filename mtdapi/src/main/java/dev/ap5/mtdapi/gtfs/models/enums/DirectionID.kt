package dev.ap5.mtdapi.gtfs.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Indicates the direction of travel for a trip.
 *
 * This field should not be used in routing; it provides a way to separate trips by direction when publishing time tables.
 *
 * @property value  0 for inbound, 1 for outbound.
 *
 * @see [GTFS Reference](https://gtfs.org/schedule/reference/#direction_id)
 */
@Serializable
enum class DirectionID(val value: Int) {
    @SerialName("0")
    INBOUND(0),

    @SerialName("1")
    OUTBOUND(1)
}