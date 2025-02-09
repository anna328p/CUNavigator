package dev.ap5.mtdapi.rest.misc

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class CachePolicy(
    val cacheType: CacheType,
    val ttl: Duration
) {
    enum class CacheType {
        NONE,
        EPHEMERAL,
        PERSISTENT
    }

    companion object { fun none() = CachePolicy(CacheType.NONE, 0.seconds)

        fun ephemeral(duration: Duration)  = CachePolicy(CacheType.EPHEMERAL,  duration)
        fun persistent(duration: Duration) = CachePolicy(CacheType.PERSISTENT, duration)

        fun ephemeral(ttl: CacheTTL)  = ephemeral(ttl.duration)
        fun persistent(ttl: CacheTTL) = persistent(ttl.duration)
    }
}