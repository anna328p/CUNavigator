package dev.ap5.mtdapi.rest.models

import dev.ap5.mtdapi.ids.StopID
import dev.ap5.mtdapi.ids.VehicleID
import dev.ap5.mtdapi.rest.models.common.Location
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * A vehicle. Usually a bus.
 *
 * Vehicles send their GPS position over a very limited bandwidth data-radio.
 * Because of this they only update their position every minute or so.
 * Do not poll for a specific vehicle more than once ever 60 seconds.
 * There will be little use in doing this anyway as the information will probably not have changed.
 *
 * The vehicle_id is unique to each vehicle and indicates the vehicle's model year
 * (e.g. 1352 is from 2013).
 *
 * @property id                 vehicle number associated with vehicle
 * @property trip               current trip information for the vehicle
 * @property location           the last known latitude and longitude of the vehicle
 * @property previousStopId     the last stop that the vehicle served
 * @property nextStopId         the next stop that the vehicle will serve
 * @property originStopId       the stop where the vehicle began its trip
 * @property destinationStopId  the stop where the vehicle will end its trip
 * @property lastUpdated        the last time the vehicle sent a real-time location update
 * @property year               the vehicle's model year
 */
@Serializable
data class Vehicle(
    @SerialName("vehicle_id")
    val id : VehicleID,

    val trip : Trip,

    val location: Location,

    @SerialName("previous_stop_id")
    val previousStopId : StopID? = null,

    @SerialName("next_stop_id")
    val nextStopId : StopID? = null,

    @SerialName("origin_stop_id")
    val originStopId : StopID? = null,

    @SerialName("destination_stop_id")
    val destinationStopId : StopID? = null,

    @SerialName("last_updated")
    val lastUpdated : Instant,
) : MTDModel() {
    val year : Int
        get() = id.year
}