package dev.ap5.mtdapi.gtfs.models.enums

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class CalendarDatesExceptionType(val value: Int) {
    @SerialName("1")
    SERVICE_ADDED(1),

    @SerialName("2")
    SERVICE_REMOVED(2)
}