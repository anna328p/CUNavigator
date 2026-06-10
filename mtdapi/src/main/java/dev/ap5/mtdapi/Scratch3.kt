package dev.ap5.mtdapi

import dev.ap5.mtdapi.rest.MTDApiClient
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlin.time.Instant

fun main() {
	runBlocking {
		val apiKey = System.getenv("MTD_API_KEY")
			?: error("Set the MTD_API_KEY environment variable to run this scratch script")
		val mtd = MTDApiClient.buildClient(apiKey)

		val query = "terminal"
		println("Query: $query")

		val res = mtd.autocomplete(query)

		res.forEach { entry ->
			val stop = entry.result
			val stopID = stop.id

			val stopInfo = mtd.getStop(stopID).unwrap()!!
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

				val toLocal = { t : Instant ->
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
		val routeGroups = routes.groupBy { it.color }
			.mapKeys { (value) -> value.map { it.longName }.minBy { it.length } }
		routeGroups.forEach { (key, value) ->
			println("$key:")
			value.forEach { route ->
				println("\t$route")
			}
		}
	}
}