package dev.ap5.mtdapi.gtfs

import dev.ap5.mtdapi.gtfs.models.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.csv.Csv
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.openZip

data class GTFSFeed(
    // Base

    val agency : List<Agency>,
    val stops : List<Stop>,
    val routes : List<Route>,
    val calendarEntries : List<CalendarEntry>,
    val calendarDates : List<CalendarDate>,
    val trips : List<Trip>,
    val stopTimes : List<StopTime>,

    // Add-ons

    val feedInfo : List<FeedInfo>? = null,
    val shapes : List<ShapePoint>? = null,

    // Fares v1

    val fareAttributes : List<FareAttributes>? = null,
    val fareRules : List<FareRule>? = null,

    // Route-based fares

    val networks : List<Network>? = null,
    val routeNetworks : List<RouteNetwork>? = null,
) {
    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        private val csv = Csv {
            recordSeparator = "\r\n"
            hasHeaderRecord = true
            ignoreUnknownColumns = true
            ignoreEmptyLines = true
        }

        @OptIn(ExperimentalSerializationApi::class)
        private inline fun <reified T : GTFSModel> process(
            map: Map<String, String>,
            key: String,
            serializer: KSerializer<T>
        ) : List<T> {
            val fileContents = map.getValue(key).trimEnd()

            return csv.decodeFromString(ListSerializer(serializer), fileContents)
        }

        fun loadFromArchive(gtfsPath: Path) : GTFSFeed {
            val zipFS = FileSystem.SYSTEM.openZip(gtfsPath)

            val paths = zipFS
                .listRecursively("/".toPath())
                .filter { zipFS.metadata(it).isRegularFile }
                .toList()

            val map = paths.associate { path ->
                path.name to zipFS.source(path).buffer().readUtf8()
            }

            return GTFSFeed(
                agency          = process(map, "agency.txt",          Agency.serializer()),
                calendarEntries = process(map, "calendar.txt",        CalendarEntry.serializer()),
                calendarDates   = process(map, "calendar_dates.txt",  CalendarDate.serializer()),
                fareAttributes  = process(map, "fare_attributes.txt", FareAttributes.serializer()),
                fareRules       = process(map, "fare_rules.txt",      FareRule.serializer()),
                feedInfo        = process(map, "feed_info.txt",       FeedInfo.serializer()),
                networks        = process(map, "networks.txt",        Network.serializer()),
                routeNetworks   = process(map, "route_networks.txt",  RouteNetwork.serializer()),
                routes          = process(map, "routes.txt",          Route.serializer()),
                shapes          = process(map, "shapes.txt",          ShapePoint.serializer()),
                stops           = process(map, "stops.txt",           Stop.serializer()),
                stopTimes       = process(map, "stop_times.txt",      StopTime.serializer()),
                trips           = process(map, "trips.txt",           Trip.serializer()),
            )
        }
    }
}