package dev.ap5.mtdapi.rest.misc

import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

enum class CacheTTL(val duration: Duration) {
    ZERO(0.seconds),
    REALTIME(30.seconds),
    SHORT(1.hours),
    LONG(24.hours)
}