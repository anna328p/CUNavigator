package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.ids.NetworkID
import dev.ap5.mtdapi.ids.RouteID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Rules to assign routes to networks.
 *
 * Conditionally Forbidden: Forbidden if `network_id` exists in routes.txt. Optional otherwise.
 *
 * @property networkID  Identifies a network to which one or multiple `route_id`s belong.
 *                      Foreign ID referencing networks.network_id.
 *
 * @property routeID    Identifies a route. Foreign ID referencing routes.route_id.
 */
@Serializable
data class RouteNetwork(
    @SerialName("network_id")
    val networkID: NetworkID,

    @SerialName("route_id")
    val routeID: RouteID
) : GTFSModel