package dev.ap5.mtdapi.rest

import dev.ap5.mtdapi.ids.*

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import de.jensklingenberg.ktorfit.http.Tag

import dev.ap5.mtdapi.rest.misc.CachePolicy
import dev.ap5.mtdapi.rest.misc.CacheTTL
import dev.ap5.mtdapi.rest.models.enums.Minimize
import dev.ap5.mtdapi.rest.models.enums.ArriveDepart
import dev.ap5.mtdapi.rest.misc.IDList
import dev.ap5.mtdapi.rest.misc.MApiResult
import dev.ap5.mtdapi.rest.responses.AutocompleteResult
import dev.ap5.mtdapi.rest.responses.MTDResponseBody
import dev.ap5.mtdapi.rest.models.APIUsageDay
import dev.ap5.mtdapi.rest.models.CalendarDate
import dev.ap5.mtdapi.rest.models.Departure
import dev.ap5.mtdapi.rest.models.Itinerary
import dev.ap5.mtdapi.rest.models.Route
import dev.ap5.mtdapi.rest.models.ShapePoint
import dev.ap5.mtdapi.rest.models.Stop
import dev.ap5.mtdapi.rest.models.StopTime
import dev.ap5.mtdapi.rest.models.Trip
import dev.ap5.mtdapi.rest.models.Vehicle

interface MTDApi {
    // Calendar dates

    /**
     * Get a list of calendar dates based on a specific date.
     *
     * The results provide all the `service_id`s that run on this particular date.
     * Each trip references a `calendar_date` through the `service_id`
     * to indicate which dates the trip operates.
     *
     * @param date         date (YYYY-MM-DD)
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getcalendardatesbydate")
    suspend fun getCalendarDatesByDate(
        @Query("date") date: LocalDate,
        @Query("changeset_id") changesetID: ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ): MApiResult<List<CalendarDate>>

    /**
     * Get a list of calendar dates based on a specific `service_id`.
     *
     * The results provide all the dates that this specific service runs on.
     * Each trip references a `calendar_date` through the `service_id`
     * to indicate which dates the trip operates.
     *
     * @param serviceID    id of the service
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getcalendardatesbyservice")
    suspend fun getCalendarDatesByService(
        @Query("service_id") serviceID : ServiceID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<CalendarDate>>

    // Departures

    /**
     * Get a list of real-time departures for a specific `stop_id`.
     *
     * **Do not poll this more than once a minute per stop or attempt to
     *   retrieve data for every stop.**
     *
     * These are live, stop-centric results based on GPS and the latest information in our system.
     * We've tried to pack as much useful data into the results so you can use this in conjunction
     * with the other methods to piece together all the information about a given bus in real-time.
     *
     * There are a few things to note about the data provided here. The `is_monitored` field does
     * not imply you should show the scheduled time! The `expected` value is based on the last
     * known location of the bus and could still be more accurate. If `is_scheduled` field is
     * false, the `trip` element will be blank because this trip was no scheduled previously.
     * It was added supplementary to the regularly scheduled service.
     *
     * The `trip` and `shape_id` depict the trip that the bus will use to pass this given stop;
     * however, this may not be the values for the bus at this moment. For instance, you may see
     * a 5E Green that's 40 minutes out. The `trip` and `shape_id` will be for a 5E, but the bus
     * might currently be a 5W Green that will eventually become a 5E! Please keep this in mind
     * when you're mapping results. We hope to be able to provide the current values later.
     *
     * To get results for all the stops at an intersection, drop the number and colon from
     * the `stop_id` (ex. `IT:1` -> `IT`).
     *
     * The `vehicle_id` is unique to each vehicle and also indicates the date the vehicle
     * was acquired (e.g. 0958 is from 2009).
     *
     * @param stopID       id of the stop (ex. `IT:1` or `IT`)
     * @param routeID      id of the route
     * @param previewTime  preview time in minutes between 0 and 60 (30 by default)
     * @param count        maximum number of departures you would like to receive
     */
    @GET("getdeparturesbystop")
    suspend fun getDeparturesByStop(
        @Query("stop_id") stopID : StopID,
        @Query("route_id") routeID : RouteID? = null,
        @Query("pt") previewTime : Int? = null,
        @Query("count") count : Int? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.ephemeral(CacheTTL.REALTIME)
    ) : MApiResult<List<Departure>>

    /**
     * @see getDeparturesByStop
     *
     * @param stopID       id of the stop (ex. `IT:1` or `IT`)
     * @param routeIDs     [IDList] of route IDs
     * @param previewTime  preview time in minutes between 0 and 60 (30 by default)
     * @param count        maximum number of departures you would like to receive
     */
    @GET("getdeparturesbystop")
    suspend fun getDeparturesByStop(
        @Query("stop_id") stopID : StopID,
        @Query("route_id") routeIDs : IDList<RouteID>?,
        @Query("pt") previewTime : Int? = null,
        @Query("count") count : Int? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.ephemeral(CacheTTL.REALTIME)
    ) : MApiResult<List<Departure>>

    // Routes

    /**
     * Get information about a specific route(s).
     *
     * These results contain metadata about the routes including name, number, and hex colors.
     *
     * @param id           route ID
     * @param changesetID  changeset ID (see [ChangesetID])
     *
     * @return [MTDResponseBody] containing [Route] objects
     */
    @GET("getroute")
    suspend fun getRoute(
        @Query("id") id : RouteID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<Route>

    /**
     * @see getRoute
     *
     * @param ids          [IDList] of route IDs
     * @param changesetID  changeset ID (see [ChangesetID])
     *
     * @return [MTDResponseBody] containing [Route] objects
     */
    @GET("getroute")
    suspend fun getRoute(
        @Query("id") ids : IDList<RouteID>,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<Route>>

    /**
     * Get the complete list of routes.
     *
     * The results contain metadata about the routes including name, number, and hex colors.
     *
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getroutes")
    suspend fun getRoutes(
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<Route>>

    /**
     * Get a list of routes that service a given stop.
     *
     * This method provides a stop-centric view of the routes.
     * The results contain metadata about the routes including name, number, and hex colors.
     *
     * @param stopID       id of the stop
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getroutesbystop")
    suspend fun getRoutesByStop(
        @Query("stop_id") stopID: StopID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<Route>>

    // Shapes

    /**
     * Get a list of points that define the shape of the route on a map.
     *
     * These points describe the path of the route on a map and how far a bus travels along that
     * path. It does not indicate what stops the bus will service! To get this information,
     * use `GetStopTimesByTrip`.
     *
     * To cut back on the amount of points, each path has been simplified in comparison to the path
     * in the GTFS feed. This means that the `shape_pt_sequence` may not be contiguous, but will
     * still convey the correct order.
     *
     * Keep in mind that not all shape points directly correlate to stops so the `stop_id`
     * may or may not exist.
     *
     * @param shapeID      id of the shape
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getshape")
    suspend fun getShape(
        @Query("shape_id") shapeID: ShapeID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<ShapePoint>>

    /**
     * Get a list of points that define the shape of part of a shape on a map.
     *
     * These points describe the path of the a trip on the map (i.e. results from planned trips).
     * It is limited by a beginning and ending stop point. It does not indicate all the stops the
     * bus will service! To get this information, use GetStopTimesByTrip.
     *
     * To cut back on the amount of points, each path has been simplified in comparison to the path
     * in the GTFS feed. This means that the `shape_pt_sequence` may not be contiguous, but will
     * still convey the correct order.
     *
     * Keep in mind that not all shape points directly correlate to stops so the `stop_id`
     * may or may not exist.
     *
     * @param beginStopID  id of the beginning stop point
     * @param endStopID    id of the ending stop point
     * @param shapeID      id of the shape
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getshapebetweenstops")
    suspend fun getShapeBetweenStops(
        @Query("begin_stop_id") beginStopID : StopID,
        @Query("end_stop_id") endStopID: StopID,
        @Query("shape_id") shapeID: ShapeID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<ShapePoint>>

    // Stops

    /**
     * Get information for a given stop.
     *
     * @param stopID       id of the stop; can be a parent stop or stop point
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getstop")
    suspend fun getStop(
        @Query("stop_id") stopID: StopID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<Stop>

    /**
     * @see getStop
     *
     * @param stopIDs      [IDList] of stop IDs; can be parent stops or stop points
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getstop")
    suspend fun getStop(
        @Query("stop_id") stopIDs: IDList<StopID>,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<Stop>>

    /**
     * Get a complete list of stops.
     *
     * This is a complete list of all 2500+ stops. You shouldn't request this frequently.
     * Check [the MTD docs] for information about using the RSS feed to stay up to date.
     *
     * [the MTD docs]: https://developer.mtd.org
     *
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getstops")
    suspend fun getStops(
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<Stop>>

    /**
     * Get a list of stops nearest a given latitude/longitude.
     *
     * Defaults to the 20 nearest stops, but you can expand it to as many as you need.
     *
     * @param lat          latitude
     * @param lon          longitude
     * @param count        number of stops to return
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getstopsbylatlon")
    suspend fun getStopsByLatLon(
        @Query("lat") lat : Double,
        @Query("lon") lon : Double,
        @Query("count") count : Int? = null,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<Stop>>

    /**
     * Get a list of stops with scheduled information about this trip.
     *
     * Use this method to find out which stops a trip will service and the scheduled times.
     * You can also use the stop_sequence to find out the next or previous stops.
     *
     * MTD uses a 30 hour clock. Any times past 24:00:00 are technically part of the next day,
     * but they're still considered part of the same service day.
     *
     * @param tripID       id of trip
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getstoptimesbytrip")
    suspend fun getStopTimesByTrip(
        @Query("trip_id") tripID: TripID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<StopTime>>

    /**
     * Get a list of schedule information for a given stop.
     *
     * This method lets you get a schedule catered to a specific stop. You can also filter it by
     * route(s) or date to fit your needs.
     *
     * MTD uses a 30 hour clock. Any times past 24:00:00 are technically part of the next day,
     * but they're still considered part of the same service day.
     *
     * @param stopID       id of stop
     * @param routeID      id of route
     * @param date         scheduled date
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getstoptimesbystop")
    suspend fun getStopTimesByStop(
        @Query("stop_id") stopID: StopID,
        @Query("route_id") routeID: RouteID? = null,
        @Query("date") date : LocalDate? = null,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<StopTime>>

    /**
     * @see getStopTimesByStop
     *
     * @param stopID       id of stop
     * @param routeIDs     [IDList] of route IDs
     * @param date         scheduled date
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("getstoptimesbystop")
    suspend fun getStopTimesByStop(
        @Query("stop_id") stopID: StopID,
        @Query("route_id") routeIDs: IDList<RouteID>?,
        @Query("date") date : LocalDate? = null,
        @Query("changeset_id") changesetID : ChangesetID? = null,
        @Tag cachePolicy: CachePolicy = CachePolicy.persistent(CacheTTL.LONG)
    ) : MApiResult<List<StopTime>>

    // Trip planning

    /**
     * Gets a list of possible itineraries based on an origin and destination
     * latitude and longitude.
     *
     * The results provide up to three itineraries for completing the requested trip.
     * Each itinerary contains legs which can either contain walk or service objects.
     * Walk objects indicate a leg of the trip that will require walking.
     * A service object indicates a leg of the trip that will require bus service.
     *
     * There can be multiple service objects in a leg. This indicates an interline
     * (i.e. a bus starts out as one route and switches to another) and means the passenger
     * should stay on the bus! In this case both lines will show up as services on the same leg.
     *
     * Although this method will accept any valid latitude/longitude coordinates, it will only
     * return meaningful results for coordinates near our service area.
     *
     * If `itineraries` list is empty (e.g. there's no service at the time specified),
     * you can use `msg` to get more details.
     *
     * @param originLat       latitude of the origin  ( -90 :  90)
     * @param originLon       longitude of the origin (-180 : 180)
     * @param destinationLat  latitude of the origin  ( -90 :  90)
     * @param destinationLon  longitude of the origin (-180 : 180)
     * @param date            date (YYYY-MM-DD)
     * @param time            time (HH:MM)
     * @param maxWalkMiles    maximum allowed walking distance in miles (.1 : 1); default .5 miles
     * @param minimize        minimize walking, transfers, or time
     * @param arriveDepart    whether to plan the trip to arrive or depart at the specified time
     */
    @GET("getplannedtripsbylatlon")
    suspend fun getPlannedTripsByLatLon(
        @Query("origin_lat") originLat : Double,
        @Query("origin_lon") originLon : Double,
        @Query("destination_lat") destinationLat : Double,
        @Query("destination_lon") destinationLon : Double,
        @Query("date") date : LocalDate? = null,
        @Query("time") time : LocalTime? = null,
        @Query("max_walk") maxWalkMiles : Double? = null,
        @Query("minimize") minimize : Minimize? = Minimize.TIME,
        @Query("arrive_depart") arriveDepart : ArriveDepart? = ArriveDepart.DEPART,
        @Tag cachePolicy: CachePolicy = CachePolicy.none()
    ) : MApiResult<List<Itinerary>>

    /**
     * Gets a list of possible itineraries based on an origin and destination `stop_id`.
     *
     * @see getPlannedTripsByLatLon
     *
     * @param originStopID  stop ID of the origin
     * @param destStopID    stop ID of the destination
     * @param date          date (YYYY-MM-DD)
     * @param time          time (HH:MM)
     * @param maxWalkMiles  maximum allowed walking distance in miles (.1 : 1); default .5 miles
     * @param minimize      minimize walking, transfers, or time
     * @param arriveDepart  whether to plan the trip to arrive or depart at the specified time
     */
    @GET("getplannedtripsbystops")
    suspend fun getPlannedTripsByStops(
        @Query("origin_stop_id") originStopID : StopID,
        @Query("destination_stop_id") destStopID: StopID,
        @Query("date") date : LocalDate? = null,
        @Query("time") time : LocalTime? = null,
        @Query("max_walk") maxWalkMiles : Double? = null,
        @Query("minimize") minimize : Minimize? = Minimize.TIME,
        @Query("arrive_depart") arriveDepart : ArriveDepart? = ArriveDepart.DEPART,
        @Tag cachePolicy: CachePolicy = CachePolicy.none()
    ) : MApiResult<List<Itinerary>>

    // Trips

    /**
     * Get information for a given trip.
     *
     * @param tripID       id of the trip
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("gettrip")
    suspend fun getTrip(
        @Query("trip_id") tripID: TripID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MApiResult<Trip>

    /**
     * @see getTrip
     *
     * @param tripIDs      [IDList] of trip IDs
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("gettrip")
    suspend fun getTrip(
        @Query("trip_id") tripIDs: IDList<TripID>,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MApiResult<List<Trip>>

    /**
     * Get trip information for a given block.
     *
     * Keep in mind that not all trips for the same route service the same stops
     * or use the same shape.
     *
     * Trips are ordered by first `arrival_time`.
     *
     * @param blockID      the block that you would like the trips for
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("gettripsbyblock")
    suspend fun getTripsByBlock(
        @Query("block_id") blockID: BlockID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MApiResult<List<Trip>>

    /**
     * Get a list of trips by route.
     * Keep in mind that not all trips for the same route service the same stops or use the
     * same shape. This list represents all the trips for the given route on all service types,
     * though not all service types operate on the same day.
     *
     * @param routeID      id of route
     * @param changesetID  changeset ID (see [ChangesetID])
     */
    @GET("gettripsbyroute")
    suspend fun getTripsByRoute(
        @Query("route_id") routeID: RouteID,
        @Query("changeset_id") changesetID : ChangesetID? = null,
    ) : MApiResult<List<Trip>>

    // Vehicles

    /**
     * Get a vehicle's real-time location by vehicle_id.
     *
     * **Do not poll this more than once per minute or attempt to retrieve data for every vehicle.**
     *
     * These are live, vehicle-centric results based on GPS and the latest information in our
     * system. We've tried to pack as much useful data into the results so you can use this in
     * conjunction with the other methods to piece together all the information about a given
     * vehicle in real-time.
     *
     * Vehicles send their GPS position over a very limited bandwidth data-radio. Because of this,
     * they only update their position every minute or so. Do not poll for a specific vehicle more
     * than once ever 60 seconds. There will be little use in doing this anyway as the information
     * will probably not have changed.
     *
     * @param vehicleID  id of the vehicle
     */
    @GET("getvehicle")
    suspend fun getVehicle(
        @Query("vehicle_id") vehicleID: VehicleID,
    ) : MApiResult<Vehicle>

    /**
     * Get information for all currently tracked vehicles.
     *
     * **Do not poll this more than once per minute or attempt to retrieve data for every vehicle.**
     *
     * @see getVehicle
     */
    @GET("getvehicles")
    suspend fun getVehicles() : MApiResult<List<Vehicle>>

    /**
     * Get information for currently tracked vehicles by route ID.
     *
     * The route_id parameter can be an [IDList] of route_ids. This can be useful if you want
     * to get vehicles for several associated routes such as the GREEN and GREENHOPPER.
     *
     * @see getVehicle
     *
     * @param routeID  id of the route for which you want vehicles
     */
    @GET("getvehiclesbyroute")
    suspend fun getVehiclesByRoute(
        @Query("route_id") routeID: RouteID,
    ) : MApiResult<List<Vehicle>>

    /**
     * @see getVehiclesByRoute
     * @see getVehicle
     *
     * @param routeIDs  [IDList] of the route IDs for which you want vehicles
     */
    @GET("getvehiclesbyroute")
    suspend fun getVehiclesByRoute(
        @Query("route_id") routeIDs: IDList<RouteID>,
    ) : MApiResult<List<Vehicle>>

    // Misc

    /**
     * Get a summary of the API usage for your key.
     *
     * These results show how many requests were made by your API key within the date range
     * specified. The results are broken down by day, then by API version, and finally by API
     * method. For your convenience, the total number of method calls is aggregated at each level.
     *
     * The four optional parameters (`method`, `version`, `start_date`, and `end_date`) act as
     * filters; using them effectively just returns a subset of the data that you would obtain by
     * not specifying any parameters. In order to conserve CPU and network resources, though, we
     * encourage you to use these filters whenever you can.
     *
     * The default date range begins on February 7, 2013 and ends “yesterday”.
     * (February 7, 2013 is the day we began using Google Analytics to track API usage.)
     * Data is not available for dates earlier than 2/7/13 or later than yesterday, and specifying
     * a `start_date` or `end_date` outside this range will result in a 400 error.
     *
     * @param apiMethod  a single API method for which you want data (e.g. `GetDeparturesByStop`)
     * @param version    a single API version for which you want data (e.g. `2.1`)
     * @param startDate  the first day for which you want data (cannot be earlier than 2013-02-07)
     * @param endDate    the last day for which you want data (cannot be later than yesterday)
     */
    @GET("getapiusage")
    suspend fun getAPIUsage(
        @Query("method") apiMethod : String? = null,
        @Query("version") version : String? = null,
        @Query("start_date") startDate : LocalDate? = null,
        @Query("end_date") endDate : LocalDate? = null,
    ) : MApiResult<List<APIUsageDay>>

    /**
     * Gets the last time the static API data was updated from the GTFS feed.
     *
     * This method is designed to help with caching either in conjunction with
     * or in place of the changeset_id.
     *
     * @return [MTDResponseBody] containing the field [MTDResponseBody.lastUpdated]
     */
    @GET("getlastfeedupdate")
    suspend fun getLastFeedUpdate() : MTDResponseBody

    /**
     * Gets a list of stops based on a query for use with autocompleting in applications.
     *
     * @param query  the search query
     *
     * @return a list of search results
     */
    @GET("https://search.mtd.org/v1.0.0/stop/suggest/{query}")
    suspend fun autocomplete(
        @Path("query") query : String
    ) : List<AutocompleteResult>
}