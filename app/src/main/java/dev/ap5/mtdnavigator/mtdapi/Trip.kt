package dev.ap5.mtdnavigator.mtdapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 *  A trip is an individual run of a route at a specific time.
 *  While multiple trips may follow the same stop pattern,
 *  a trip is a single instance of that pattern.
 *
 *  @property id         id of trip
 *  @property headsign   information usually shown on headsign
 *  @property routeId    id of route
 *  @property serviceId  id of service
 *  @property shapeId    id of shape
 *  @property blockId    id of block
 *  @property direction  direction of trip
 */
@Serializable data class Trip(
    @SerialName("trip_id")
    val id : TripID,

    @SerialName("trip_headsign")
    val headsign : String,

    @SerialName("route_id")
    val routeId : RouteID,

    @SerialName("service_id")
    val serviceId : ServiceID,

    @SerialName("shape_id")
    val shapeId : ShapeID,

    @SerialName("block_id")
    val blockId : BlockID,

    val direction : String,
)