package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.gtfs.serializers.GTFSDateSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Dataset metadata, including publisher, version, and expiration information.
 *
 * Conditionally required: Required if translations.txt is provided. Recommended otherwise.
 *
 * @property publisherName    Full name of the organization that publishes the dataset.
 *
 * @property publisherURL     URL of the dataset publishing organization's website.
 *
 * @property language         Default language used for the text in this dataset. Language code.
 *
 * @property defaultLanguage  Defines the language that should be used when the data consumer
 *                            doesn’t know the language of the rider. Language code.
 *
 * @property startDate        The dataset provides complete and reliable schedule information for
 *                            service in the period from the beginning of the `feed_start_date` day
 *                            to the end of the `feed_end_date` day.
 *
 * @property endDate          (see above) Date.
 *
 * @property version          String that indicates the current version of their GTFS dataset.
 *
 * @property contactEmail     Email address for communication regarding the GTFS dataset
 *                            and data publishing practices.
 *
 * @property contactURL       URL for contact information, a web-form, support desk, or other
 *                            tools for communication regarding the GTFS dataset and data
 *                            publishing practices.
 */
@Serializable
data class FeedInfo(
    @SerialName("feed_publisher_name")
    val publisherName: String,

    @SerialName("feed_publisher_url")
    val publisherURL: String,

    @SerialName("feed_lang")
    val language: String,

    @SerialName("default_lang")
    val defaultLanguage: String? = null,

    @SerialName("feed_start_date")
    @Serializable(with = GTFSDateSerializer::class)
    val startDate: LocalDate? = null,

    @SerialName("feed_end_date")
    @Serializable(with = GTFSDateSerializer::class)
    val endDate: LocalDate? = null,

    @SerialName("feed_version")
    val version: String? = null,

    @SerialName("feed_contact_email")
    val contactEmail: String? = null,

    @SerialName("feed_contact_url")
    val contactURL: String? = null
) : GTFSModel