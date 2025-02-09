package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.gtfs.models.enums.CalendarDatesExceptionType
import dev.ap5.mtdapi.gtfs.serializers.GTFSDateSerializer
import dev.ap5.mtdapi.ids.ServiceID
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Exceptions for the services defined in the calendar.txt.
 *
 * Conditionally required: Required if calendar.txt is omitted, in which case calendar_dates.txt
 * must contain all dates of service. Optional otherwise.
 *
 * @property serviceID      Identifies a set of dates when a service exception occurs for
 *                          one or more routes.
 * @property date           Date when service exception occurs.
 * @property exceptionType  Indicates whether service is available on the date specified
 *                          in the date field.
 */
@Serializable
data class CalendarDate(
    @SerialName("service_id")
    val serviceID: ServiceID,

    @Serializable(with = GTFSDateSerializer::class)
    val date: LocalDate,

    @SerialName("exception_type")
    val exceptionType: CalendarDatesExceptionType
) : GTFSModel