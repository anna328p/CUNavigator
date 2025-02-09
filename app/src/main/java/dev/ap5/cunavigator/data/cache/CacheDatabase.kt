package dev.ap5.cunavigator.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.ap5.cunavigator.data.RoomConverters

@Database(entities = [CacheEntry::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun cacheDao() : CacheDao
}