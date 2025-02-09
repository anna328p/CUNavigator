package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.ids.LevelID
import dev.ap5.mtdapi.ids.ZoneID
import dev.ap5.mtdapi.gtfs.models.enums.LocationType
import dev.ap5.mtdapi.gtfs.models.enums.WheelchairBoarding
import dev.ap5.mtdapi.ids.StopID
import kotlinx.datetime.TimeZone
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
/**
 * Stops where vehicles pick up or drop off riders. Also defines stations and station entrances.
 *
 * Conditionally required: Optional if demand-responsive zones are defined in locations.geojson. Required otherwise.
 *
 * @property id                  Identifies a location: stop/platform, station, entrance/exit,
 *                               generic node or boarding area (see location_type).
 *
 * @property code                Short text or a number that identifies the location for riders.
 *
 * @property name                Name of the location.
 *                               Conditionally required: Required for locations which are stops
 *                               (`location_type=0`), stations (`location_type=1`) or
 *                               entrances/exits (`location_type=2`). Optional for locations
 *                               which are generic nodes (`location_type=3`) or boarding areas
 *                               (`location_type=4`).
 *
 * @property ttsName             Readable version of the `stop_name`.
 *
 * @property description         Description of the location that provides useful, quality
 *                               information. Should not be a duplicate of `stop_name`.
 *
 * @property latitude            Latitude of the location.
 *
 * @property longitude           Longitude of the location.
 *
 * @property zoneID              Identifies the fare zone for a stop. ID.
 *
 * @property url                 URL of a web page about the location. URL.
 *
 * @property locationType        Location type. Enum.
 *
 * @property parentStationID     Defines hierarchy between the different locations
 *                               defined in stops.txt.
 *                               Foreign ID referencing stops.stop_id.
 *
 * @property timeZone            Timezone of the location. Timezone.
 *
 * @property wheelchairBoarding  Indicates whether wheelchair boardings are possible
 *                               from the location.
 *
 * @property levelID             Level of the location.
 *                               Foreign ID referencing levels.level_id.
 *
 * @property platformCode        Platform identifier for a platform stop
 *                               (a stop belonging to a station).
 */
@Serializable
data class Stop(
    @SerialName("stop_id")
    val id: StopID,

    @SerialName("stop_code")
    val code: String? = null,

    @SerialName("stop_name")
    val name: String? = null,

    @SerialName("tts_stop_name")
    val ttsName: String? = null,

    @SerialName("stop_desc")
    val description: String? = null,

    @SerialName("stop_lat")
    val latitude: Double? = null,

    @SerialName("stop_lon")
    val longitude: Double? = null,

    @SerialName("zone_id")
    val zoneID: ZoneID? = null,

    @SerialName("stop_url")
    val url: String? = null,

    @SerialName("location_type")
    val locationType: LocationType? = LocationType.STOP_PLATFORM_EMPTY,

    @SerialName("parent_station")
    val parentStationID: StopID? = null,

    @SerialName("stop_timezone")
    val timeZone: TimeZone? = null,

    @SerialName("wheelchair_boarding")
    val wheelchairBoarding: WheelchairBoarding? = WheelchairBoarding.INHERIT_FROM_PARENT_EMPTY,

    @SerialName("level_id")
    val levelID: LevelID? = null,

    @SerialName("platform_code")
    val platformCode: String? = null
) : GTFSModel