package dev.ap5.mtdapi

import dev.ap5.mtdapi.gtfs.GTFSFeed
import okio.Path.Companion.toPath

fun main() {
    val feed = GTFSFeed.loadFromArchive("/home/anna/work/CUNavigator/google_transit.zip".toPath())

    println(feed.stopTimes.take(5))

    // println(feed)
}