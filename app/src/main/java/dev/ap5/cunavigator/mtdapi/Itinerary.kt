package dev.ap5.cunavigator.mtdapi

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Each itinerary contains legs which can either contain walk or service objects.
 * Walk objects indicate a leg of the trip that will require walking.
 * A service object indicates a leg of the trip that will require bus service.
 *
 * There can be multiple service objects in a leg.
 * This indicates an interline (i.e. A bus starts out as one route and switches to another)
 * and means the passenger should stay on the bus!
 * In this case both lines will show up as services on the same leg.
 *
 * Although this method will accept any valid latitude/longitude coordinates,
 * it will only return meaningful results for coordinates near the MTD service area.
 */
@Serializable
data class Itinerary(
    @SerialName("start_time")
    val startTime : Instant,

    @SerialName("end_time")
    val endTime : Instant,

    @SerialName("travel_time")
    val travelTime : Double,

    val legs : List<Leg>,
) {
    /**
     * A single leg in an itinerary. This can be either riding or walking.
     */
    @Serializable
    sealed class Leg {
        /**
         * Represents the start or end point of a leg object.
         *
         * @property lat     latitude
         * @property lon     longitude
         * @property name    name of the terminus
         * @property time    estimated arrival time
         * @property stopId  stop ID
         */
        @Serializable
        data class Terminus(
            val lat : Double,
            val lon : Double,
            val name : String,
            val time : Instant,

            @SerialName("stop_id")
            val stopId : StopID? = null,
        )
    }

    /**
     * @property services  the leg's service objects
     */
    @Serializable
    @SerialName("Service")
    data class ServiceLeg(
        val services : List<Segment>
    ) : Leg() {
        /**
         * A leg of the journey that requires riding.
         *
         * There can be multiple service objects in a leg.
         * This indicates an interline (i.e. A bus starts out as one route and switches to another)
         * and means the passenger should stay on the bus!
         * In this case both lines will show up as services on the same leg.
         *
         * @property begin  the starting point for a leg
         * @property end    the ending point for a leg
         * @property route  the route providing service for this leg
         * @property trip   the trip providing service for this leg
         */
        @Serializable
        data class Segment(
            val begin : Terminus,
            val end : Terminus,
            val route : Route,
            val trip : Trip,
        )
    }

    /**
     * @property walk  the leg's walk object
     */
    @Serializable
    @SerialName("Walk")
    data class WalkLeg(
        val walk : Segment
    ) : Leg() {
        /**
         * A leg of the journey that requires walking.
         * @property begin      the starting point for a leg
         * @property end        the ending point for a leg
         * @property direction  direction in which to walk
         * @property distance   how far this leg has you walk
         */
        @Serializable
        data class Segment(
            val begin : Terminus,
            val end : Terminus,
            val direction : String,
            val distance : Double,
        )
    }
}