package dev.ap5.cunavigator.mtdapi

import retrofit2.http.GET

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import retrofit2.http.Path
import retrofit2.http.Query

interface MTDApi {
    // Calendar dates

    @GET("getcalendardatesbydate")
    suspend fun getCalendarDatesByDate(
        @Query("date") date : LocalDate,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    @GET("getcalendardatesbyservice")
    suspend fun getCalendarDatesByService(
        @Query("service_id") serviceID: ServiceID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    // Departures

    @GET("getdeparturesbystop")
    suspend fun getDeparturesByStop(
        @Query("stop_id") stopID: StopID,
        @Query("route_id") routeID: RouteID? = null,
        @Query("pt") previewTime : Int? = null,
        @Query("count") count : Int? = null,
    ) : MTDResponse

    // Routes

    @GET("getroute")
    suspend fun getRoute(
        @Query("id") id : RouteID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    @GET("getroutes")
    suspend fun getRoutes(
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    @GET("getroutesbystop")
    suspend fun getRoutesByStop(
        @Query("stop_id") stopID: StopID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    // Shapes

    @GET("getshape")
    suspend fun getShape(
        @Query("shape_id") shapeID: ShapeID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    @GET("getshapebetweenstops")
    suspend fun getShapeBetweenStops(
        @Query("begin_stop_id") beginStopID : StopID,
        @Query("end_stop_id") endStopID: StopID,
        @Query("shape_id") shapeID: ShapeID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    // Stops

    @GET("getstop")
    suspend fun getStop(
        @Query("stop_id") stopID: StopID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    @GET("getstops")
    suspend fun getStops(
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    @GET("getstopsbylatlon")
    suspend fun getStopsByLatLon(
        @Query("lat") lat : Double,
        @Query("lon") lon : Double,
        @Query("count") count : Int? = null,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    @GET("getstoptimesbytrip")
    suspend fun getStopTimesByTrip(
        @Query("trip_id") tripID: TripID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    @GET("getstoptimesbystop")
    suspend fun getStopTimesByStop(
        @Query("stop_id") stopID: StopID,
        @Query("route_id") routeID: RouteID? = null,
        @Query("date") date : LocalDate? = null,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    // Trip planning

    @GET("getplannedtripsbylatlon")
    suspend fun getPlannedTripsByLatLon(
        @Query("origin_lat") originLat : Double,
        @Query("origin_lon") originLon : Double,
        @Query("destination_lat") destinationLat : Double,
        @Query("destination_lon") destinationLon : Double,
        @Query("date") date : LocalDate? = null,
        @Query("time") time : LocalTime? = null,
        @Query("max_walk") maxWalk : Double? = null,
        @Query("minimize") minimize : String? = null,
        @Query("arrive_depart") arriveDepart : String? = null,
    ) : MTDResponse

    @GET("getplannedtripsbystops")
    suspend fun getPlannedTripsByStops(
        @Query("origin_stop_id") originStopID : StopID,
        @Query("destination_stop_id") destinationStopID: StopID,
        @Query("date") date : LocalDate? = null,
        @Query("time") time : LocalTime? = null,
        @Query("max_walk") maxWalk : Double? = null,
        @Query("minimize") minimize : String? = null,
        @Query("arrive_depart") arriveDepart : String? = null,
    ) : MTDResponse

    // Trips

    @GET("gettrip")
    suspend fun getTrip(
        @Query("trip_id") tripID: TripID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    @GET("gettripsbyblock")
    suspend fun getTripsByBlock(
        @Query("block_id") blockID: BlockID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    @GET("gettripsbyroute")
    suspend fun getTripsByRoute(
        @Query("route_id") routeID: RouteID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MTDResponse

    // Vehicles

    @GET("getvehicle")
    suspend fun getVehicle(
        @Query("vehicle_id") vehicleID: VehicleID,
    ) : MTDResponse

    @GET("getvehicles")
    suspend fun getVehicles() : MTDResponse

    @GET("getvehiclesbyroute")
    suspend fun getVehiclesByRoute(
        @Query("route_id") routeID: RouteID,
    ) : MTDResponse

    // Misc

    @GET("getapiusage")
    suspend fun getAPIUsage(
        @Query("method") method : String? = null,
        @Query("version") version : String? = null,
        @Query("start_date") startDate : LocalDate? = null,
        @Query("end_date") endDate : LocalDate? = null,
    ) : MTDResponse

    @GET("getlastfeedupdate")
    suspend fun getLastFeedUpdate() : MTDResponse

    @GET("https://search.mtd.org/v1.0.0/stop/suggest/{query}")
    suspend fun autocomplete(
        @Path("query") query : String
    ) : List<AutocompleteResult>
}