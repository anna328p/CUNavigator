package dev.ap5.cunavigator
import dev.ap5.cunavigator.components.MTDApiService
import dev.ap5.cunavigator.components.MTDHttpClient
import dev.ap5.cunavigator.components.MTDRetrofit
import dev.ap5.cunavigator.data.SecretsRepository
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val query = "springfield gregory"
        println("Query: ${query}")

        val mtd = MTDApiService(MTDRetrofit(MTDHttpClient(SecretsRepository()))).service

        val res = mtd.autocomplete(query)

        for (entry in res) {
            val stop = entry.result
            val stopID = stop.id
            val stopDepartures = mtd.getDeparturesByStop(stopID)

            val departures = stopDepartures.departures!!.sortedBy { it.expectedMins }

            println("\nStop: ${stop.name}\n")
            for (d in departures) {
                println("${d.expectedMins} min\t  ${d.headsign} - ${d.trip.headsign}")
            }
        }
    }
}