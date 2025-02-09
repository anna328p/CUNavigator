package dev.ap5.mtdapi.ids

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class LevelID(private val id: String) : IDType {
    override fun toString() = id
}
