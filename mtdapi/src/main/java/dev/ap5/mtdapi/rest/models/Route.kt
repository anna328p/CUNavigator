package dev.ap5.mtdapi.rest.models

import dev.ap5.mtdapi.ids.RouteID
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 *  A route is exactly what it sounds like.
 *  Keep in mind that some routes maybe split up.
 *  (e.g. GREEN, GREENHOPPER, GREEN EVENING, GREEN WEEKEND, GREENHOPPER WEEKEND, etc.)
 *
 *  @property id         id of route
 *  @property longName   long name
 *  @property shortName  short name
 *  @property color      hex color of route
 *  @property textColor  hex color of text for route
 */
@Serializable
data class Route (
    @SerialName("route_id")
    val id : RouteID,

    @SerialName("route_long_name")
    val longName : String,

    @SerialName("route_short_name")
    val shortName : String,

    @SerialName("route_text_color")
    val textColor : String,

    @SerialName("route_color")
    val color : String,
) : MTDModel()