package dev.ap5.mtdapi.rest.responses

import dev.ap5.mtdapi.ids.ChangesetID
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
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement

/**
 * Standard MTD response format.
 */
@Serializable
data class MTDResponseBody(
    /**
     * @see ChangesetID
     */
    @SerialName("changeset_id")
    val changesetID : ChangesetID? = null,

    /**
     * The time of the server when the request was processed.
     */
    val time : Instant = Instant.fromEpochSeconds(0),

    /**
     * When used in conjunction with `changeset_id`, the `new_changeset` field indicates whether
     * new data is being returned.
     *
     * If a `changeset_id` is not passed in, this field will always be `true`. If an invalid
     * `changeset_id` is passed in (i.e. the data on the server has updated), this value will
     * still be `true`. However, if a valid `changeset_id` is passed in with matching parameters,
     * `new_changeset` will be `false` indicating that your cached data is still valid
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

    @SerialName("days")
    val apiUsageDays : List<APIUsageDay>? = null,

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
         * Status code.
         *
         * The codes have the semantic meanings listed below. Note that "Not Modified" uses
         * code 200 with [newChangeset] set to `false`, not code 202!
         *
         *  Code | Description
         * :-----|:-----------------------------------------------------------
         *  200  | The request was completed successfully.
         *  400  | A parameter was invalid.
         *  401  | The key provided is invalid.
         *  403  | The hourly request limit on the given key has been reached.
         *  404  | The requested method does not exist.
         *  500  | The server encountered an error.
         */
        val code : Int,

        /** status message */
        val msg : String,
    )
}