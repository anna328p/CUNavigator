package dev.ap5.cunavigator.mtdapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * A stop is a collection of one or more stop points in a logical or geographic grouping.
 *
 * @property id          id of stop
 * @property name        name of stop
 * @property code        text message code
 * @property stopPoints  stop points that compose a parent stop
 * @property distance    distance from the stop in feet
 */
@Serializable
data class Stop(
    @SerialName("stop_id")
    val id : String,

    @SerialName("stop_name")
    val name : String,

    val code : String,

    @SerialName("stop_points")
    val stopPoints : List<StopPoint>,

    val distance : Double? = null,
)