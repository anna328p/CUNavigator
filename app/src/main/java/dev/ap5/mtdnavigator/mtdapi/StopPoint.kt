package dev.ap5.mtdnavigator.mtdapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * A stop point is a specific location where MTD vehicles will board
 * or alight passengers. Results include geographic information.
 *
 * @property id    id of stop
 * @property name  name of stop
 * @property lat   latitude of stop
 * @property lon   longitude of stop
 * @property code  text message code
 */
@Serializable
data class StopPoint (
    @SerialName("stop_id")
    val id : StopID,

    @SerialName("stop_name")
    val name : String,

    @SerialName("stop_lat")
    val lat : Double,

    @SerialName("stop_lon")
    val lon : Double,

    val code : String,
)