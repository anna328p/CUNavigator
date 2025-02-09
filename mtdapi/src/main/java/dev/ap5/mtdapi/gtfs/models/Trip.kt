package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.gtfs.models.enums.BikesAllowed
import dev.ap5.mtdapi.gtfs.models.enums.DirectionID
import dev.ap5.mtdapi.gtfs.models.enums.WheelchairAccessible
import dev.ap5.mtdapi.ids.BlockID
import dev.ap5.mtdapi.ids.RouteID
import dev.ap5.mtdapi.ids.ServiceID
import dev.ap5.mtdapi.ids.ShapeID
import dev.ap5.mtdapi.ids.TripID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Trips for each route. A trip is a sequence of two or more stops that occur during a specific time period.
 *
 * Required.
 *
 * @property routeID               Identifies a route.
 *                                 Foreign ID referencing routes.route_id.
 *
 * @property serviceID             Identifies a set of dates when service is available
 *                                 for one or more routes.
 *                                 Foreign ID referencing calendar.service_id or
 *                                 calendar_dates.service_id.
 *
 * @property id                    Identifies a trip. Unique ID.
 *
 * @property headsign              Text that appears on signage identifying the trip's
 *                                 destination to riders.
 *
 * @property shortName             Public facing text used to identify the trip to riders, for
 *                                 instance, to identify train numbers for commuter rail trips.
 *
 * @property directionID           Indicates the direction of travel for a trip.
 *
 * @property blockID               Identifies the block to which the trip belongs.
 *
 * @property shapeID               Identifies a geospatial shape describing the vehicle
 *                                 travel path for a trip.
 *
 * @property wheelchairAccessible  Indicates wheelchair accessibility.
 *
 * @property bikesAllowed          Indicates whether bikes are allowed.
 */
@Serializable
data class Trip(
    @SerialName("route_id")
    val routeID: RouteID,

    @SerialName("service_id")
    val serviceID: ServiceID,

    @SerialName("trip_id")
    val id: TripID,

    @SerialName("trip_headsign")
    val headsign: String? = null,

    @SerialName("trip_short_name")
    val shortName: String? = null,

    @SerialName("direction_id")
    val directionID: DirectionID? = null,

    @SerialName("block_id")
    val blockID: BlockID? = null,

    @SerialName("shape_id")
    val shapeID: ShapeID? = null,

    @SerialName("wheelchair_accessible")
    val wheelchairAccessible: WheelchairAccessible? = null,

    @SerialName("bikes_allowed")
    val bikesAllowed: BikesAllowed? = null
) : GTFSModel