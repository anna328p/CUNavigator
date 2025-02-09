package dev.ap5.mtdapi.gtfs.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PaymentMethod(val value: Int) {
    @SerialName("0")
    ON_BOARD(0),

    @SerialName("1")
    BEFORE_BOARDING(1)
}