package dev.ap5.mtdapi.gtfs.models

import dev.ap5.mtdapi.ids.BookingRuleID
import dev.ap5.mtdapi.ids.LocationGroupID
import dev.ap5.mtdapi.ids.LocationID
import dev.ap5.mtdapi.gtfs.models.enums.ContinuousPickupDropOff
import dev.ap5.mtdapi.gtfs.models.enums.PickupDropOffType
import dev.ap5.mtdapi.gtfs.models.enums.Timepoint
import dev.ap5.mtdapi.ids.StopID
import dev.ap5.mtdapi.ids.TripID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Times that a vehicle arrives at and departs from stops for each trip.
 *
 * Required.
 *
 * @property tripID                    Identifies a trip.
 *                                     Foreign ID referencing trips.trip_id.
 *
 * @property arrivalTime               Arrival time at the stop (defined by stop_times.stop_id)
 *                                     for a specific trip (defined by stop_times.trip_id) in
 *                                     the time zone specified by agency.agency_timezone, not
 *                                     stops.stop_timezone.
 *
 *                                     If there are not separate times for arrival and departure at
 *                                     a stop, arrival_time and departure_time should be the same.
 *
 *                                     For times occurring after midnight on the service day, enter
 *                                     the time as a value greater than 24:00:00 in HH:MM:SS.
 *
 *                                     If exact arrival and departure times (timepoint=1) are not
 *                                     available, estimated or interpolated arrival and departure
 *                                     times (timepoint=0) should be provided.
 *
 *                                     Conditionally Required:
 *                                     - Required for the first and last stop in a trip (defined by
 *                                       stop_times.stop_sequence).
 *                                     - Required for timepoint=1.
 *                                     - Forbidden when start_pickup_drop_off_window or
 *                                       end_pickup_drop_off_window are defined.
 *                                     - Optional otherwise.
 *
 * @property departureTime             Departure time from the stop. See above.
 *
 * @property stopID                    Identifies the serviced stop.
 *                                     A stop may be serviced multiple times in the same trip, and
 *                                     multiple trips and routes may service the same stop.
 *                                     Foreign ID referencing stops.stop_id.
 *
 * @property locationGroupID           Identifies the serviced location group that indicates groups
 *                                     of stops where riders may request pickup or drop off.
 *                                     Foreign ID referencing location_groups.location_group_id.
 *
 * @property locationID                Identifies the GeoJSON location that corresponds to serviced
 *                                     zone where riders may request pickup or drop off.
 *                                     Foreign ID referencing id from locations.geojson.
 *
 * @property stopSequence              Order of stops, location groups, or GeoJSON locations
 *                                     for a particular trip.
 *
 * @property headsign                  Text that appears on signage identifying the trip's
 *                                     destination to riders.
 *
 * @property startPickupDropOffWindow  Time that on-demand service becomes available in a GeoJSON
 *                                     location, location group, or stop.
 *
 * @property endPickupDropOffWindow    Time that on-demand service ends in a GeoJSON location,
 *                                     location group, or stop.
 *
 * @property pickupType                Indicates pickup method.
 *
 * @property dropOffType               Indicates drop off method.
 *
 * @property continuousPickup          Indicates that the rider can board the transit vehicle at
 *                                     any point along the vehicle’s travel path as described by
 *                                     shapes.txt, from this `stop_time` to the next `stop_time`
 *                                     in the trip’s `stop_sequence`.
 *
 * @property continuousDropOff         Indicates that the rider can alight from the transit vehicle
 *                                     at any point along the vehicle’s travel path as described by
 *                                     shapes.txt, from this `stop_time` to the next `stop_time` in
 *                                     the trip’s `stop_sequence`.
 *
 * @property shapeDistanceTraveled     Actual distance traveled along the associated shape, from
 *                                     the first stop to the stop specified in this record.
 *
 * @property timepoint                 Indicates if arrival and departure times for a stop are
 *                                     strictly adhered to by the vehicle or if they are instead
 *                                     approximate and/or interpolated times.
 *
 * @property pickupBookingRuleID       Identifies the boarding booking rule at this stop time.
 *                                     Foreign ID referencing booking_rules.booking_rule_id.
 *
 * @property dropOffBookingRuleID      Identifies the alighting booking rule at this stop time.
 *                                     Foreign ID referencing booking_rules.booking_rule_id.
 */
@Serializable
data class StopTime(
    @SerialName("trip_id")
    val tripID: TripID,

    @SerialName("arrival_time")
    val arrivalTime: String? = null, // Time in HH:MM:SS format

    @SerialName("departure_time")
    val departureTime: String? = null, // Time in HH:MM:SS format

    @SerialName("stop_id")
    val stopID: StopID? = null,

    @SerialName("location_group_id")
    val locationGroupID: LocationGroupID? = null,

    @SerialName("location_id")
    val locationID: LocationID? = null,

    @SerialName("stop_sequence")
    val stopSequence: Int,

    @SerialName("stop_headsign")
    val headsign: String? = null,

    @SerialName("start_pickup_drop_off_window")
    val startPickupDropOffWindow: String? = null, // Time in HH:MM:SS format

    @SerialName("end_pickup_drop_off_window")
    val endPickupDropOffWindow: String? = null, // Time in HH:MM:SS format

    @SerialName("pickup_type")
    val pickupType: PickupDropOffType? = PickupDropOffType.REGULARLY_SCHEDULED_EMPTY,

    @SerialName("drop_off_type")
    val dropOffType: PickupDropOffType? = PickupDropOffType.REGULARLY_SCHEDULED_EMPTY,

    @SerialName("continuous_pickup")
    val continuousPickup: ContinuousPickupDropOff? = null,

    @SerialName("continuous_drop_off")
    val continuousDropOff: ContinuousPickupDropOff? = null,

    @SerialName("shape_dist_traveled")
    val shapeDistanceTraveled: Float? = null,

    @SerialName("timepoint")
    val timepoint: Timepoint? = null,

    @SerialName("pickup_booking_rule_id")
    val pickupBookingRuleID: BookingRuleID? = null,

    @SerialName("drop_off_booking_rule_id")
    val dropOffBookingRuleID: BookingRuleID? = null
) : GTFSModel