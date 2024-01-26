package dev.ap5.mtdnavigator.mtdapi

import kotlinx.serialization.Serializable

/**
 * If two trips have the same block_id,
 * that indicates that these trips will be operated by the same vehicle.
 */
@Serializable
@JvmInline
value class BlockID(private val id : String) {
    override fun toString() = id
}
