package dev.ap5.cunavigator.data.cache

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InMemoryCache @Inject constructor(
    @ApplicationContext private val applicationContext : Context,
) : CacheProvider {
    override val service : CacheDatabase by lazy {
        Room.inMemoryDatabaseBuilder(
            applicationContext,
            CacheDatabase::class.java
        ).build()
    }
}