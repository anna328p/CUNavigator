package dev.ap5.mtdapi.rest.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class APIUsageDay(
    val date : LocalDate,
    val calls : Int,
    val versions : List<Version>,
) : MTDModel() {
    @Serializable
    data class Version(
        val number : String,
        val calls : Int,
        val methods : List<Method>,
    ) {
        @Serializable
        data class Method(
            val name : String,
            val calls : Int,
        )
    }
}