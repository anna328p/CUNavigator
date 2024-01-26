package dev.ap5.mtdnavigator.mtdapi

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class StopID(private val id : String) {
    override fun toString() = id
}
