package dev.ap5.cunavigator.mtdapi

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement

/**
 * Standard MTD response format.
 */
@Serializable
data class MTDResponse(
    /**
     * Many of the method calls return large datasets that change very infrequently
     * (e.g. GetStops). To better accommodate caching we have included a changeset_id
     * to be returned with any cacheable dataset as part of the response (rsp).
     * It is determined by the date the data was updated and the parameters
     * you send with the request.
     *
     * Once a dataset has been returned with a changeset_id, submitting that changeset_id
     * with the same parameters will generate a 202 "not modified" response from the server,
     * indicating that the data has not changed.
     * If the data has changed, you'll get a response like normal.
     * If the changeset_id, parameters, or data on the server does not match the original request,
     * you'll receive a new dataset.
     */
    @SerialName("changeset_id")
    val changesetId : ChangesetID? = null,

    /**
     * The time of the server when the request was processed.
     */
    val time : Instant = Instant.fromEpochSeconds(0),

    /**
     * When used in conjunction with changeset_id, the new_changeset field indicates
     * whether new data is being returned.
     * If a changeset_id is not passed in, this field will always be true.
     * If an invalid changeset_id is passed in (i.e. the data on the server has updated),
     * this value will still be true.
     * However, if a valid changeset_id is passed in with matching parameters,
     * new_changeset will be false indicating that your cached data is still valid
     * and the response is empty.
     */
    @SerialName("new_changeset")
    val newChangeset : Boolean = false,

    val status : Status,

    @SerialName("rqst")
    val request : Request = Request(),

    // All the possible response fields

    @SerialName("calendar_dates")
    val calendarDates : List<CalendarDate>? = null,

    val departures : List<Departure>? = null,

    val routes : List<Route>? = null,

    @SerialName("shapes")
    val shapePoints : List<ShapePoint>? = null,

    val stops : List<Stop>? = null,

    @SerialName("stop_times")
    val stopTimes : List<StopTime>? = null,

    val itineraries : List<Itinerary>? = null,

    val trips : List<Trip>? = null,

    val vehicles : List<Vehicle>? = null,

    val days : List<APIUsageDay>? = null,

    @SerialName("last_updated")
    val lastUpdated : Instant? = null,
) {
    @Serializable
    data class Request(
        val method : String = "",
        val params : Map<String, JsonElement> = mapOf(),
    )

    @Serializable
    data class Status(
        /**
         * 200 	The request was completed successfully.
         * 400 	A parameter was invalid.
         * 401 	The key provided is invalid.
         * 403 	The hourly request limit on the given key has been reached.
         * 404 	The requested method does not exist.
         * 500 	The server encountered an error.
         */
        val code : Int,

        val msg : String,
    )
}