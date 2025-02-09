package dev.ap5.mtdapi.rest.responses

import dev.ap5.mtdapi.ids.StopID
import kotlinx.serialization.Serializable

/**
 * A list of stops based on a query for use with autocompleting in applications.
 */
@Serializable
data class AutocompleteResult(
    /**
     * The stop search result.
     */
    val result : ResultBlock,

    /**
     * The search score of a stop. Because we are using prefix matching,
     * this will almost always be one.
     */
    val score : Double,

    /**
     * An array of objects to use for query highlighting search results.
     * Each object represents a word.
     */
    val queryHighlight : List<HighlightSegment>,
) {
    /**
     * An object used for highlighting in search results.
     * Each object represents a word and contains a `highlightPart` and a `noHighlightPart`.
     */
    @Serializable
    data class HighlightSegment(
        /**
         * The part of the word in the `queryHighlight` that matches the input query
         * and should be highlighted.
         */
        val highlightPart : String? = null,

        /**
         * The part of the word in the `queryHighlight` that **does not** match the input query
         * and should not be highlighted.
         */
        val noHighlightPart : String? = null,
    )

    /**
     * A stop search result.
     *
     * @property id        The ID of the stop.
     * @property smsCode   The SMS code for the stop. Note that searching by SMS code is supported.
     * @property name      The name of the stop.
     * @property city      The city in which the stop is located.
     * @property isParent  If the stop is a parent stop or a boarding point.
     */
    @Serializable
    data class ResultBlock(
        val id : StopID,
        val smsCode : String,
        val name : String,
        val city : String,
        val isParent : Boolean,

        /**
         * An approximation of the popularity of a stop.
         * All things being equal, the search service tries to return more popular stops first.
         */
        val rank : Int,
    )
}