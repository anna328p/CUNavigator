package dev.ap5.mtdapi.ids

import kotlinx.serialization.Serializable

/**
 * Many of the method calls return large datasets that change very infrequently
 * (e.g. [dev.ap5.mtdapi.MTDApi.getStops]). To better accommodate caching we have included a
 * `changeset_id` to be returned with any cacheable dataset as part of the response (`rsp`).
 * It is determined by the date the data was updated and the parameters you send with the request.
 *
 * Once a dataset has been returned with a `changeset_id`, submitting that `changeset_id` with the
 * same parameters will generate a 202 "not modified" response from the server, indicating that
 * the data has not changed. If the data has changed, you'll get a response like normal. If the
 * `changeset_id`, parameters, or data on the server does not match the original request, you'll
 * receive a new dataset.
 */
@Serializable
@JvmInline
value class ChangesetID(val id : String) : IDType {
	override fun toString() = id
}