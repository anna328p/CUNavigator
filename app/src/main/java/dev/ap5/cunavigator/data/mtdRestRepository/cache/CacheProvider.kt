package dev.ap5.cunavigator.data.mtdRestRepository.cache

interface CacheProvider {
    val service : CacheDatabase

    val dao : CacheDao
        get() = service.cacheDao()

    fun clear() {
        dao.clear()
    }
}