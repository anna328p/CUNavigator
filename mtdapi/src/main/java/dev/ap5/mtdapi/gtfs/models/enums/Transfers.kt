package dev.ap5.mtdapi.gtfs.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Transfers(val value: Int) {
    @SerialName("0")
    NO_TRANSFERS(0),

    @SerialName("1")
    ONE_TRANSFER(1),

    @SerialName("2")
    TWO_TRANSFERS(2),

    @SerialName("")
    UNLIMITED_TRANSFERS(-1) // Representing empty string as null
}