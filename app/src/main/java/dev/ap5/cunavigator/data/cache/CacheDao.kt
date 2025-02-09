package dev.ap5.cunavigator.data.cache

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CacheDao {
    @Query("select * from cache_entries where \"key\" = :key")
    fun getEntry(key : String) : CacheEntry

    @Query("delete from cache_entries")
    fun clear()
}