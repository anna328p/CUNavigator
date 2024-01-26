package dev.ap5.mtdnavigator.mtdapi

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.http.GET

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.json.Json
import retrofit2.http.Query

private const val API_VERSION = "v2.2"
private const val BASE_URL = "https://developer.mtd.org/api/${API_VERSION}/json/"

private const val API_KEY = "REDACTED_MTD_API_KEY"

private val httpClient = OkHttpClient.Builder()
    .addInterceptor(Interceptor { chain -> chain.proceed(
        chain.request().newBuilder()
            .url(chain.request().url.newBuilder()
                .addQueryParameter("key", API_KEY)
                .build())
            .build())
    } )
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .validateEagerly(true)
    .build()

interface MTDApiService {
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
}

object MTDApi {
    val service : MTDApiService by lazy {
        retrofit.create(MTDApiService::class.java)
    }
}