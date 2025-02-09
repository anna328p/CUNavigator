package dev.ap5.mtdapi.ids

import kotlinx.serialization.Serializable

/**
 * The vehicle_id is unique to each vehicle and indicates the vehicle's model year
 * (e.g. 1352 is from 2013).
 */
@Serializable
@JvmInline
value class VehicleID(private val id : String) : IDType {
    override fun toString() = id

    /** the vehicle's model year, parsed from the ID string */
    val year : Int
        get() {
            val yearEnd = id.slice(0..1).toInt()

            return when (yearEnd) {
                in  0..70 -> 2000 + yearEnd
                in 70..99 -> 1900 + yearEnd
                else      -> throw IllegalArgumentException("invalid year $yearEnd")
            }
        }
}