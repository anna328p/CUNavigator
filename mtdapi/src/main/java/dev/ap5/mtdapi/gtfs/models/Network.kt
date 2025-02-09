package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.ids.NetworkID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Network grouping of routes.
 *
 * Conditionally Forbidden: Forbidden if `network_id` exists in routes.txt. Optional otherwise.
 *
 * @property id    Identifies a network. Unique ID.
 * @property name  The name of the network that apply for fare leg rules,
 *                 as used by the local agency and its riders.
 */
@Serializable
data class Network(
    @SerialName("network_id")
    val id: NetworkID,

    @SerialName("network_name")
    val name: String
) : GTFSModel