package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.ids.AgencyID
import dev.ap5.mtdapi.ids.NetworkID
import dev.ap5.mtdapi.gtfs.models.enums.ContinuousPickupDropOff
import dev.ap5.mtdapi.gtfs.models.enums.RouteType
import dev.ap5.mtdapi.ids.RouteID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Transit routes. A route is a group of trips that are displayed to riders as a single service.
 *
 * Required.
 *
 * @property id                 Identifies a route. Unique ID.
 *
 * @property agencyID           Agency for the specified route.
 *                              Conditionally required: Required if multiple agencies are
 *                              defined in agency.txt. Recommended otherwise.
 *                              Foreign ID referencing agency.agency_id.
 *
 * @property shortName          Short name of a route.
 *                              Conditionally required: Required if routes.route_long_name is empty.
 *                              Recommended if there is a brief service designation.
 *
 * @property longName           Full name of a route.
 *                              Conditionally required: Required if routes.route_short_name
 *                              is empty. Optional otherwise.
 *
 * @property description        Description of a route that provides useful, quality information.
 *                              Should not be a duplicate of `route_short_name` or `route_long_name`.
 *
 * @property type               Indicates the type of transportation used on a route. Enum.
 *
 * @property url                URL of a web page about the particular route.
 *                              Should be different from the agency.agency_url value.
 *
 * @property color              Route color designation that matches public facing material.
 *                              Defaults to white (`FFFFFF`) when omitted or left empty.
 *
 * @property textColor          Legible color to use for text drawn against a background
 *                              of `route_color`. Defaults to black (`000000`) when omitted
 *                              or left empty.
 *
 * @property sortOrder          Orders the routes in a way which is ideal for presentation
 *                              to customers. Routes with smaller `route_sort_order` values
 *                              should be displayed first.
 *
 * @property continuousPickup   Indicates that the rider can board the transit vehicle at any point
 *                              along the vehicle’s travel path as described by shapes.txt, on every
 *                              trip of the route.
 *
 * @property continuousDropOff  Indicates that the rider can alight from the transit vehicle at any
 *                              point along the vehicle’s travel path as described by shapes.txt,
 *                              on every trip of the route.
 *
 * @property networkID          Identifies a group of routes.
 *                              Conditionally Forbidden: Forbidden if the route_networks.txt
 *                              file exists. Optional otherwise.
 */
@Serializable
data class Route(
    @SerialName("route_id")
    val id: RouteID,

    @SerialName("agency_id")
    val agencyID: AgencyID? = null,

    @SerialName("route_short_name")
    val shortName: String? = null,

    @SerialName("route_long_name")
    val longName: String? = null,

    @SerialName("route_desc")
    val description: String? = null,

    @SerialName("route_type")
    val type: RouteType,

    @SerialName("route_url")
    val url: String? = null,

    @SerialName("route_color")
    val color: String? = null,

    @SerialName("route_text_color")
    val textColor: String? = null,

    @SerialName("route_sort_order")
    val sortOrder: Int? = null,

    @SerialName("continuous_pickup")
    val continuousPickup: ContinuousPickupDropOff? = null,

    @SerialName("continuous_drop_off")
    val continuousDropOff: ContinuousPickupDropOff? = null,

    @SerialName("network_id")
    val networkID: NetworkID? = null
) : GTFSModel