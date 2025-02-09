package dev.ap5.mtdapi.rest.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * A stop time represents a scheduled arrival and departure time for a trip at a specific stop.
 *
 * MTD uses a 30 hour clock. Any times past 24:00:00 are technically part of the next day,
 * but are still considered part of the same service day.
 *
 * @property arrivalTime    scheduled time of arrival (HH:mm:ss)
 * @property departureTime  scheduled time of departure (HH:mm:ss)
 * @property stopSequence   sequence of stop
 * @property trip           trip arriving at the stop
 */
@Serializable
data class StopTime(
    @SerialName("arrival_time")
    val arrivalTime : String,

    @SerialName("departure_time")
    val departureTime : String,

    @SerialName("stop_sequence")
    val stopSequence : String,

    val trip : Trip,
) : MTDModel()