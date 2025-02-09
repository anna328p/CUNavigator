package dev.ap5.mtdapi.ids

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class FareID(private val id: String) : IDType {
    override fun toString() = id
}
