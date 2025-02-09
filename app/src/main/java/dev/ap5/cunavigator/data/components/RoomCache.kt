package dev.ap5.cunavigator.data.components

import dev.ap5.cunavigator.data.cache.CacheProvider
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val provider : CacheProvider
) {
}