package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.ids.AgencyID
import dev.ap5.mtdapi.ids.FareID
import dev.ap5.mtdapi.gtfs.models.enums.PaymentMethod
import dev.ap5.mtdapi.gtfs.models.enums.Transfers
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Fare information for a transit agency's routes.
 *
 * Optional.
 *
 * @property fareID            Identifies a fare class.
 * @property price             Fare price, in the unit specified by `currency_type`
 * @property currencyType      Currency used to pay the fare. Currency code.
 * @property paymentMethod     Indicates when the fare must be paid.
 * @property transfers         Indicates the number of transfers permitted on this fare.
 *
 * @property agencyID          Identifies the relevant agency for a fare.
 *                             Conditionally required: Required if multiple agencies
 *                             are defined in agency.txt. Recommended otherwise.
 *                             Foreign ID referencing agency.agency_id.
 *
 * @property transferDuration  Length of time in seconds before a transfer expires.
 *                             When `transfers`=`0` this field may be used to indicate
 *                             how long a ticket is valid, or it may be left empty.
 */
@Serializable
data class FareAttributes(
    @SerialName("fare_id")
    val fareID: FareID,

    val price: Float,

    @SerialName("currency_type")
    val currencyType: String,

    @SerialName("payment_method")
    val paymentMethod: PaymentMethod,

    val transfers: Transfers?,

    @SerialName("agency_id")
    val agencyID: AgencyID? = null,

    @SerialName("transfer_duration")
    val transferDuration: Int? = null
) : GTFSModel