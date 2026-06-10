package dev.ap5.cunavigator.data.mtdRestRepository

import dev.ap5.cunavigator.data.components.MTDApiService
import dev.ap5.cunavigator.data.mtdRestRepository.cache.InMemoryCache
import dev.ap5.cunavigator.data.mtdRestRepository.cache.PersistentCache
import dev.ap5.mtdapi.ids.ChangesetID
import dev.ap5.mtdapi.ids.RouteID
import dev.ap5.mtdapi.ids.StopID
import dev.ap5.mtdapi.ids.VehicleID
import dev.ap5.mtdapi.rest.MTDApi
import dev.ap5.mtdapi.rest.misc.IDList
import dev.ap5.mtdapi.rest.misc.MApiResult
import dev.ap5.mtdapi.rest.misc.MResult
import dev.ap5.mtdapi.rest.models.Stop
import dev.ap5.mtdapi.rest.models.Vehicle
import kotlinx.datetime.LocalDate
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Instant

class MTDRestRepository @Inject constructor(
	private val apiService : MTDApiService,
	private val inMemoryCache : InMemoryCache,
	private val persistentCache : PersistentCache,
) {
	private val mtd : MTDApi by lazy {
		apiService.service
	}

	private val CACHE_TTL_STANDARD = 24.hours

	data class CacheEntry<out ObjType, TagType>(
		val obj : ObjType,
		val tag : TagType,
		val time : Instant,
	)

	private var stopCache : MutableMap<StopID, CacheEntry<Stop, ChangesetID>> = mutableMapOf()

	private var stopsCache : CacheEntry<List<Stop>, ChangesetID>? = null

	suspend fun getStop(stopID : StopID) : Stop? {
		return cachedRequest<Stop?>(
			fetch = { tag ->
				mtd.getStop(stopID, changesetID = tag)
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
			fetch = { tag -> mtd.getStops(changesetID = tag) },
			cacheGet = { stopsCache },
			cachePut = { obj, tag ->
				stopsCache = CacheEntry(obj, tag, Clock.System.now())
			},
			ttl = CACHE_TTL_STANDARD
		)
	}

	suspend fun getVehicle(vehicleID : VehicleID) : Vehicle? {
		return mtd.getVehicle(vehicleID).unwrap()
	}

	suspend fun getVehicles() : List<Vehicle> {
		return mtd.getVehicles().unwrap()!!
	}

	suspend fun getCalendarDatesByDate(date : LocalDate) {
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
	private inline fun <TResult> cachedRequest(
		fetch : (ChangesetID?) -> MApiResult<TResult>,
		cacheGet : () -> CacheEntry<TResult, ChangesetID>?,
		cachePut : (TResult, ChangesetID) -> Unit,
		ttl : Duration,
	) : TResult {
		val entry = cacheGet()

		if (entry != null && Clock.System.now() - entry.time > ttl)
			return entry.obj

		val new = fetch(entry?.tag)

		val [obj, tag] = when (entry) {
			null -> new.unwrapChangeset()
			else -> when (new) {
				is MResult.NotModified -> Pair(entry.obj, entry.tag)
				else                   -> new.unwrapChangeset()
			}
		}

		cachePut(obj!!, tag!!)

		return obj
	}
}