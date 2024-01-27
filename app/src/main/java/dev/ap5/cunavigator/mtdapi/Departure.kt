package dev.ap5.cunavigator.mtdapi

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *  A departure represents a bus approaching a given stop.
 *  Results include real-time departure time, route information,
 *  and vehicle location regarding the bus.
 *
 *  @property stopId         id of stop
 *  @property headsign       information usually shown on headsign
 *  @property route          route information for the trip
 *  @property trip           trip information for the departure
 *  @property vehicleId      id associated with vehicle
 *  @property origin         trip's origin stop
 *  @property destination    trip's destination stop
 *  @property isMonitored    whether the vehicle is communicating
 *  @property isScheduled    whether the trip was scheduled
 *  @property isIStop        whether this trip can be boarded without a fare/pass
 *  @property scheduledTime  scheduled departure time of the bus for the given stop
 *  @property expectedTime   expected departure time of the bus for the given stop
 *  @property expectedMins   number of minutes before expected departure time
 *  @property location       latitude and longitude of vehicle
 */
@Serializable
data class Departure(
    @SerialName("stop_id")
    val stopId : StopID,

    val headsign : String,

    val route : Route,

    val trip : Trip,

    @SerialName("vehicle_id")
    val vehicleId : VehicleID,

    val origin : Terminus,

    val destination : Terminus,

    @SerialName("is_monitored")
    val isMonitored : Boolean,

    @SerialName("is_scheduled")
    val isScheduled : Boolean,

    @SerialName("is_istop")
    val isIStop : Boolean,

    @SerialName("scheduled")
    val scheduledTime : Instant,

    @SerialName("expected")
    val expectedTime : Instant,

    @SerialName("expected_mins")
    val expectedMins : Int,

    val location : Location,
) {
    /**
     * Represents the start or end point of a route.
     * @property stopId id of stop
     */
    @Serializable
    data class Terminus(
        @SerialName("stop_id")
        val stopId : StopID,
    )
}