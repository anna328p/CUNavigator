package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.ids.ShapeID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Rules for mapping vehicle travel paths, sometimes referred to as route alignments.
 *
 * @property shapeID       Identifies a shape
 *
 * @property lat           Latitude of a shape point
 *
 * @property lon           Longitude of a shape point
 *
 * @property sequence      Sequence in which the shape points connect to form the shape.
 *
 * @property distTraveled  Actual distance traveled along the shape from the first shape point
 *                         to the point specified in this record.
 */
@Serializable
data class ShapePoint(
    @SerialName("shape_id")
    val shapeID: ShapeID,

    @SerialName("shape_pt_lat")
    val lat: Double,

    @SerialName("shape_pt_lon")
    val lon: Double,

    @SerialName("shape_pt_sequence")
    val sequence: Int,

    @SerialName("shape_dist_traveled")
    val distTraveled: Float? = null
) : GTFSModel