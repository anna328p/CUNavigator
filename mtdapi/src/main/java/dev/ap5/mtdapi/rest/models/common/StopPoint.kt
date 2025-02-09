package dev.ap5.mtdapi.rest.models.common

import dev.ap5.mtdapi.ids.StopID
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

/**
 * A stop point is a specific location where MTD vehicles will board
 * or alight passengers. Results include geographic information.
 *
 * @property id    id of stop
 * @property name  name of stop
 * @property lat   latitude of stop
 * @property lon   longitude of stop
 * @property code  text message code
 *
 * @see dev.ap5.mtdapi.models.Stop
 */
@Serializable
data class StopPoint (
    @SerialName("stop_id")
    val id : StopID,

    @SerialName("stop_name")
    val name : String,

    @SerialName("stop_lat")
    val lat : Double,

    @SerialName("stop_lon")
    val lon : Double,

    val code : String,
) {
    /**
     * Short name of the stop, parsed from `name` by scanning for text between parentheses.
     */
    val shortName : String
        get() {
            val re = Regex("""\((.+)\)""")

            return when (val match = re.find(this.name)) {
                null -> this.name
                else -> match.groupValues[1]
            }
        }

    /**
     * The ID of the parent stop, parsed from `id` by dropping the number and colon.
     */
    val parentStopID : String
        get() {
            val re = Regex("""(\w+):\d+""")
            val idStr = this.id.toString()

            return when (val match = re.find(idStr)) {
                null -> idStr
                else -> match.groupValues[1]
            }
        }
}