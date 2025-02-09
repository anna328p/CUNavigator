package dev.ap5.mtdapi.rest.models.common

import kotlinx.serialization.Serializable

/**
 * Represents a location on Earth stored as a coordinate pair.
 *
 * @property lat latitude  ( -90 :  90)
 * @property lon longitude (-180 : 180)
 */
@Serializable
data class Location(
    val lat : Double,
    val lon : Double,
)