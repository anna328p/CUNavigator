package dev.ap5.cunavigator.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.ap5.mtdapi.ids.ChangesetID
import dev.ap5.mtdapi.rest.responses.MTDResponseBody
import kotlinx.datetime.Instant

@Entity(tableName = "cache_entries")
data class CacheEntry(
    @PrimaryKey val key : String,
    val changesetID : ChangesetID,
    val body : MTDResponseBody,
    val createdAt : Instant,
    val expiresAt : Instant?,
)