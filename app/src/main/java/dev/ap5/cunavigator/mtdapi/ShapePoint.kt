package dev.ap5.cunavigator.mtdapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A point that defines the shape of a route on the map.
 *
 * @property distTraveled  total distance traveled to this point
 * @property lat           latitude of point
 * @property lon           longitude of point
 * @property stopId        the stop id associated with the shape point
 * @property sequence      sequence of point in GTFS feed
 */
@Serializable
data class ShapePoint(
    @SerialName("shape_dist_traveled")
    val distTraveled : Double,

    @SerialName("shape_pt_lat")
    val lat : Double,

    @SerialName("shape_pt_lon")
    val lon : Double,

    @SerialName("shape_pt_sequence")
    val sequence : Int,

    @SerialName("stop_id")
    val stopId : StopID?,
)