package dev.ap5.mtdnavigator
import kotlinx.coroutines.runBlocking
import dev.ap5.mtdnavigator.mtdapi.*

fun main() {
    runBlocking {
        val res = MTDApi.service.getStop(StopID("IT"))
        //val res = MTDApi.service.getRoutes()

        println(res)
    }
}