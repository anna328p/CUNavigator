package dev.ap5.mtdapi

import dev.ap5.mtdapi.gtfs.GTFSFeed
import dev.ap5.mtdapi.ids.StopID
import okio.Path.Companion.toPath

fun main() {
    val feed = GTFSFeed.loadFromArchive("/home/anna/work/CUNavigator/google_transit.zip".toPath())

    val sid = StopID("IT")

    val stop = feed.stops.find { it.id == sid }!!

    println(stop)
    println("${stop.id}\t  [${stop.code}]\t  ${stop.name}")
    println()

    println("Stop points:")
    val stopPoints = feed.stops
        .filter { it.id.toString().startsWith(stop.id.toString() + ":") && it.id != stop.id }

    stopPoints.forEach { point ->
        println("\t  ${point.id}\t  [${point.code}]\t  ${point.name}")
    }

    println()

    println("Routes:")

    val routeGroups = feed.stopTimes
        .filter { it.stopID!!.parentStopID == stop.id }
        .map { time -> feed.trips.find { it.id == time.tripID }!! }
        .map { it.routeID }
        .distinct()
        .map { routeID -> feed.routes.find { it.id == routeID }!! }
        .groupBy { it.id.toString().split(' ').first() }
        .mapValues { (value) -> value.sortedBy { it.shortName!!.toInt() } }

    routeGroups.forEach { (key, value) ->
        println("$key:")
        value.forEach { route ->
            println("\t${route.shortName}\t  ${route.longName}")
        }
    }
}