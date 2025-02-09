package dev.ap5.mtdapi.gtfs.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Timepoint(val value: Int) {
    @SerialName("0")
    APPROXIMATE(0),

    @SerialName("1")
    EXACT(1)
}