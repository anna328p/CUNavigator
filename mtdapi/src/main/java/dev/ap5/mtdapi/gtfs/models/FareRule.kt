package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.ids.FareID
import dev.ap5.mtdapi.ids.ZoneID
import dev.ap5.mtdapi.ids.RouteID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Rules to apply fares for itineraries.
 *
 * Optional.
 *
 * @property fareID         Identifies a fare class.
 *                          Foreign ID referencing fare_attributes.fare_id.
 *
 * @property routeID        Identifies a route associated with the fare class.
 *                          Foreign ID referencing routes.route_id.
 *
 * @property originID       Identifies an origin zone.
 *                          Foreign ID referencing stops.zone_id.
 *
 * @property destinationID  Identifies a destination zone.
 *                          Foreign ID referencing stops.zone_id.
 *
 * @property containsID     Identifies the zones that a rider will enter while using the fare class.
 *                          Foreign ID referencing stops.zone_id.
 */
@Serializable
data class FareRule(
    @SerialName("fare_id")
    val fareID: FareID,

    @SerialName("route_id")
    val routeID: RouteID? = null,

    @SerialName("origin_id")
    val originID: ZoneID? = null,

    @SerialName("destination_id")
    val destinationID: ZoneID? = null,

    @SerialName("contains_id")
    val containsID: ZoneID? = null
) : GTFSModel