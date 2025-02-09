package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.gtfs.serializers.GTFSDateSerializer
import dev.ap5.mtdapi.ids.ServiceID
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Service dates specified using a weekly schedule with start and end dates.
 *
 * Conditionally required: Required unless all dates of service are defined in calendar_dates.txt. Optional otherwise.
 *
 * @property serviceID  Identifies a set of dates when service is available for one or more routes.
 * @property monday     Indicates whether the service operates on all Mondays in the date range
 *                      specified by the `start_date` and `end_date` fields.
 * @property tuesday    Functions in the same way as `monday` except applies to Tuesdays.
 * @property wednesday  Functions in the same way as `monday` except applies to Wednesdays.
 * @property thursday   Functions in the same way as `monday` except applies to Thursdays.
 * @property friday     Functions in the same way as `monday` except applies to Fridays.
 * @property saturday   Functions in the same way as `monday` except applies to Saturdays.
 * @property sunday     Functions in the same way as `monday` except applies to Sundays.
 * @property startDate  Start service day for the service interval.
 * @property endDate    End service day for the service interval, inclusive.
 */
@Serializable
data class CalendarEntry(
    @SerialName("service_id")
    val serviceID: ServiceID,

    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val saturday: Boolean,
    val sunday: Boolean,

    @SerialName("start_date")
    @Serializable(with = GTFSDateSerializer::class)
    val startDate: LocalDate,

    @SerialName("end_date")
    @Serializable(with = GTFSDateSerializer::class)
    val endDate: LocalDate
) : GTFSModel