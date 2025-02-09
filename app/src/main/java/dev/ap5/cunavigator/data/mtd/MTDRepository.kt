package dev.ap5.cunavigator.data.mtd

import dev.ap5.cunavigator.data.cache.InMemoryCache
import dev.ap5.cunavigator.data.components.MTDApiService
import dev.ap5.cunavigator.data.cache.PersistentCache
import dev.ap5.mtdapi.ids.*
import dev.ap5.mtdapi.rest.misc.IDList
import dev.ap5.mtdapi.models.*
import dev.ap5.mtdapi.responses.*
import dev.ap5.mtdapi.rest.MTDApi
import dev.ap5.mtdapi.rest.models.Stop
import dev.ap5.mtdapi.rest.models.Vehicle
import dev.ap5.mtdapi.rest.responses.MTDResponseBody
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

class MTDRepository @Inject constructor(
    private val apiService : MTDApiService,
    private val inMemoryCache: InMemoryCache,
    private val persistentCache: PersistentCache,
) {
    private val mtd : MTDApi by lazy {
        apiService.service
    }

    private val CACHE_TTL_STANDARD = 24.hours

    data class CacheEntry<out ObjType, TagType>(val obj: ObjType, val tag: TagType, val time: Instant)

    private var stopCache : MutableMap<StopID, CacheEntry<Stop, ChangesetID>> = mutableMapOf()

    private var stopsCache : CacheEntry<List<Stop>, ChangesetID>? = null

    private inline fun wrappedRequest(
        f : () -> MTDResponseBody
    ) : MTDResponseBody? {
        return f()
        /*
        val resp = f()

        return when (resp.code()) {
            200  -> resp.body()!!
            202  -> null
            404  -> throw NoSuchElementException(resp.body()!!.status.msg)
            else -> throw IllegalStateException()
        }

         */
    }

    suspend fun getStop(stopID: StopID) : Stop? {
        return cachedRequest<Stop?, _>(
            fetch = { tag ->
                val res = wrappedRequest { mtd.getStop(stopID, changesetID = tag) }
                res?.let { Pair(res.stops!!.firstOrNull(), res.changesetID!!) }
            },
            cacheGet = { stopCache[stopID] },
            cachePut = { stop, tag ->
                if (stop != null) {
                    val entry = CacheEntry(stop, tag, Clock.System.now())
                    stopCache[stopID] = entry
                }
            },
            ttl = CACHE_TTL_STANDARD
        )
    }

    suspend fun getStops() : List<Stop> {
        return cachedRequest(
            fetch = { tag ->
                val res = wrappedRequest { mtd.getStops(changesetID = tag) }
                res?.let { Pair(it.stops!!, it.changesetID!!) }
            },
            cacheGet = { stopsCache },
            cachePut = { obj, tag ->
                stopsCache = CacheEntry(obj, tag, Clock.System.now())
            },
            ttl = CACHE_TTL_STANDARD
        )
    }

    suspend fun getVehicle(vehicleID: VehicleID) : Vehicle? {
        return wrappedRequest { mtd.getVehicle(vehicleID) }!!.vehicles?.firstOrNull()
    }

    suspend fun getCalendarDatesByDate(date: LocalDate) {
        val foo = mtd.getRoute(IDList.of("GREEN"))
        var bar = mtd.getRoute(RouteID("GREEN"))
        var baz = mtd.getRoute(IDList.of(RouteID("GREEN")))
    }

    /**
     * Wraps a request in user-provided caching logic.
     * @param fetch     Function that gets a new object from the data source
     * @param cacheGet  Function that retrieves an entry from the cache by its ID
     * @param cachePut  Function that stores a new entry in the cache
     * @param ttl       Time-to-live
     */
    private inline fun <TResult, TTag> cachedRequest(
        fetch : (TTag?) -> Pair<TResult, TTag>?,
        cacheGet : () -> CacheEntry<TResult, TTag>?,
        cachePut : (TResult, TTag) -> Unit,
        ttl : Duration
    ) : TResult {
        val entry = cacheGet()

        if (entry != null && Clock.System.now() - entry.time > ttl)
            return entry.obj

        val new = fetch(entry?.tag)

        val (obj, tag) = when (entry) {
            null -> new!!
            else -> new ?: Pair(entry.obj, entry.tag)
        }

        cachePut(obj, tag)

        return obj
    }
}