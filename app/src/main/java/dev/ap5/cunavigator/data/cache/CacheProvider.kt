package dev.ap5.cunavigator.data.cache

import dev.ap5.cunavigator.data.cache.CacheDatabase

interface CacheProvider {
    val service : CacheDatabase

    val dao : CacheDao
        get() = service.cacheDao()

    fun clear() {
        dao.clear()
    }
}