package dev.ap5.cunavigator.data

import androidx.room.TypeConverter
import dev.ap5.mtdapi.rest.responses.MTDResponseBody
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

/**
 * Provides the ability to store certain internal objects using the Room persistence library.
 */
class RoomConverters {
    /**
     * Store an MTDResponse as a JSON string
     */
    @TypeConverter
    fun fromResponse(value : MTDResponseBody) : String {
        return Json.encodeToString(value)
    }

    /**
     * Decode a stored JSON string into an MTDResponse
     */
    @TypeConverter
    fun toResponse(str : String) : MTDResponseBody {
        return Json.decodeFromString(str)
    }

    /**
     * Store an Instant as a string
     */
    @TypeConverter
    fun fromInstant(value : Instant) : String {
        return value.toString()
    }

    /**
     * Parse an Instant from a string
     */
    @TypeConverter
    fun toInstant(str : String) : Instant {
        return Instant.parse(str)
    }
}