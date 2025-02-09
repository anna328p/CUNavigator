package dev.ap5.mtdapi
import dev.ap5.mtdapi.rest.MTDApiClient
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char

fun main() {
    runBlocking {
        val mtd = MTDApiClient.buildClient("REDACTED_MTD_API_KEY")
        /*
        val res = mtd.getStops().body()!!.stops!!

        for (stop in res) {
            println("\n\nStop: ${stop.name} [${stop.id}]")

            for (point in stop.stopPoints) {
                val re = Regex("""\((.+)\)""")

                val parsed = when (val match = re.find(point.name)) {
                    null -> point.name
                    else -> match.groupValues[1]
                }

                println("Point: ${point.name} [${point.id}] | $parsed")
                println("(${point.lat}, ${point.lon})\n")
            }
        }


         */

        val query = "terminal"
        println("Query: $query")

        val res = mtd.autocomplete(query)

        res.forEach { entry ->
            val stop = entry.result
            val stopID = stop.id

            val stopInfo = mtd.getStop(stopID).unwrap()!!
            println(stopInfo)
            val points = stopInfo.stopPoints
            val pointsMap = points.associateBy({ it.id }, { it })

            val stopDepartures = mtd.getDeparturesByStop(stopID, previewTime = 60)
            val departures = stopDepartures.unwrap()!!.sortedBy { it.expectedMins }

            val stopRoutes = mtd.getRoutesByStop(stopID).unwrap()!!
            val routes = stopRoutes.sortedBy { it.shortName.toInt() }

            println("\nStop: ${stopInfo.name} [${stopInfo.id}] [${stopInfo.code}]\n")
            println("Served by: ${routes.map { it.shortName }.distinct().joinToString(", ")}\n")
            println("Stop points:")
            stopInfo.stopPoints.forEach { p ->
                println("${p.id}\t  ${p.name}\t  ${p.code}\t  ${p.lat}\t  ${p.lon}")
            }
            println()

            departures.forEach { d ->
                val point = pointsMap[d.stopId]!!

                val toLocal = { t: Instant ->
                    val centralTime = TimeZone.of("America/Chicago")
                    with(centralTime) { t.toLocalDateTime() }
                }

                val arrivalTime = toLocal(d.scheduledTime).format(LocalDateTime.Format {
                    hour(); char(':'); minute()
                })

                println("$arrivalTime (${d.expectedMins} min)\t  [${point.shortName}]\t  ${d.headsign} - ${d.trip.headsign}")
            }

        }

        val routes = mtd.getRoutes().unwrap()!!

        println("\n Route details:\n")
        val routeGroups = routes.groupBy { it.color }.mapKeys { (_, v) -> v.map { it.longName }.minBy { it.length } }
        routeGroups.forEach { (k, v) ->
            println("$k:")
            v.forEach { route ->
                println("\t$route")
            }
        }
    }
}