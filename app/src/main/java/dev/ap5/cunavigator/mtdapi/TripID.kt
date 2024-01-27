package dev.ap5.cunavigator.mtdapi

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class TripID(private val id : String) {
    override fun toString() = id
}
