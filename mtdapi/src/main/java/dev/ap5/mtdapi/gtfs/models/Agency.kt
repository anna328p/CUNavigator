package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.ids.AgencyID
import kotlinx.datetime.TimeZone
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Transit agencies with service represented in this dataset.
 *
 * A dataset may contain data from multiple agencies.
 *
 * @property id           Identifies a transit brand, often synonymous with a transit agency.
 * @property name         Full name of the transit agency.
 * @property url          URL of the transit agency.
 * @property timeZone     Timezone where the transit agency is located. If multiple agencies are
 *                        specified in the dataset, each must have the same `agency_timezone`.
 * @property language     Primary language used by this transit agency. Should be provided to help
 *                        GTFS consumers choose capitalization rules and other language-specific
 *                        settings for the dataset.
 * @property phoneNumber  A voice telephone number for the specified agency.
 * @property fareUrl      URL of a web page where a rider can purchase tickets or other fare
 *                        instruments for the agency, or a web page containing information about
 *                        the agency's fares.
 * @property email        Email address actively monitored by the agency’s customer service
 *                        department. This email address should be a direct contact point where
 *                        transit riders can reach a customer service representative at the agency.
 */
@Serializable
data class Agency(
    @SerialName("agency_id")
    val id : AgencyID? = null,

    @SerialName("agency_name")
    val name : String,

    @SerialName("agency_url")
    val url : String,

    @SerialName("agency_timezone")
    val timeZone : TimeZone,

    @SerialName("agency_lang")
    val language : String? = null,

    @SerialName("agency_phone")
    val phoneNumber : String? = null,

    @SerialName("agency_fare_url")
    val fareUrl : String? = null,

    @SerialName("agency_email")
    val email : String? = null
) : GTFSModel