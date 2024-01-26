package dev.ap5.mtdnavigator.mtdapi

import kotlinx.serialization.Serializable

/**
 * Represents a location on Earth stored as a coordinate pair.
 *
 * @property lat latitude
 * @property lon longitude
 */
@Serializable
data class Location(
    val lat : Double,
    val lon : Double,
)