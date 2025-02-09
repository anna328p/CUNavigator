package dev.ap5.mtdapi.rest.models

import dev.ap5.mtdapi.ids.ServiceID
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * A calendar date defines the service available on any given day.
 * Use this in conjunction with trips to know if that trip operates on a given day.
 *
 * @property date       a date this service operates
 * @property serviceId  id for this service type
 */
@Serializable
data class CalendarDate(
    val date : LocalDate,

    @SerialName("service_id")
    val serviceId : ServiceID,
) : MTDModel()