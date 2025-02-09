package dev.ap5.mtdapi.rest.models

import dev.ap5.mtdapi.ids.StopID
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

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
 * @property startTime the time the itinerary will begin
 * @property endTime   the time the itinerary will end
 * @property travelTime total travel time in minutes
 * @property
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
) : MTDModel() {
    /**
     * A single leg in an itinerary. This can be either riding or walking.
     */
    @Serializable
    sealed class Leg

    /**
     * A leg of the trip that requires riding.
     *
     * @property services  the leg's service segments
     */
    @Serializable
    @SerialName("Service")
    data class ServiceLeg(
        val services : List<ServiceSegment>
    ) : Leg() {
        /**
         * A leg of the journey that requires riding.
         *
         * There can be multiple service objects in a leg. This indicates an interline
         * (i.e. A bus starts out as one route and switches to another) and means the passenger
         * should stay on the bus! In this case both lines will show up as services on the same leg.
         *
         * @property begin  the starting point for a leg
         * @property end    the ending point for a leg
         * @property route  the route providing service for this leg
         * @property trip   the trip providing service for this leg
         */
        @Serializable
        data class ServiceSegment(
            val begin : Terminus,
            val end : Terminus,
            val route : Route,
            val trip : Trip,
        )
    }

    /**
     * A leg of the trip that requires walking.
     *
     * @property walk  the leg's walk segment
     */
    @Serializable
    @SerialName("Walk")
    data class WalkLeg(
        val walk : WalkSegment
    ) : Leg() {
        /**
         * A leg of the journey that requires walking.
         *
         * @property begin      the starting point for a leg
         * @property end        the ending point for a leg
         * @property direction  direction in which to walk
         * @property distance   how far this leg has you walk
         */
        @Serializable
        data class WalkSegment(
            val begin : Terminus,
            val end : Terminus,
            val direction : String,
            val distance : Double,
        )
    }

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