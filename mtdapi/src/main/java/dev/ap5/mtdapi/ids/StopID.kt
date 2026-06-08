package dev.ap5.mtdapi.ids

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class StopID(private val id : String) : IDType {
    override fun toString() = id

    val isStopPoint : Boolean
        get() = id.contains(':')

    val parentStopID : StopID
        get() {
            if (!isStopPoint) return this

            val colonIndex = id.indexOf(':')

            return StopID(id.substring(0, colonIndex))
        }
}