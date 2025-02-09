package dev.ap5.mtdapi

import dev.ap5.mtdapi.ids.RouteID
import dev.ap5.mtdapi.rest.misc.IDList
import junit.framework.TestCase.assertEquals
import org.junit.Test

class IDListTest {
    @Test
    fun testOf() {
        val routes = arrayOf("GREEN", "YELLOW")
        val routeIDs = routes.map { RouteID(it) }.toTypedArray()

        val idListOf : IDList<RouteID> = IDList.of(*routes)
        val idListCons : IDList<RouteID> = IDList(*routeIDs)

        assertEquals(idListOf, idListCons)
    }
}