package dev.ap5.mtdapi.rest.misc

import dev.ap5.mtdapi.ids.ChangesetID
import dev.ap5.mtdapi.rest.responses.MTDResponseBody

sealed interface MResult<out A, out B> {
    fun unwrap() : A?

    fun unwrapChangeset() : Pair<A?, ChangesetID?>

    data class Ok<A>(val value : A, val changesetID: ChangesetID? = null) : MResult<A, Nothing> {
        override fun unwrap() : A = value

        override fun unwrapChangeset(): Pair<A, ChangesetID?> = Pair(value, changesetID)
    }

    data class NotModified(val changesetID: ChangesetID? = null) : MResult<Nothing, Nothing> {
        override fun unwrap(): Nothing? = null

        override fun unwrapChangeset(): Pair<Nothing?, ChangesetID?> = Pair(null, changesetID)
    }

    data class Err<B>(val value : B) : MResult<Nothing, B> {
        override fun unwrap() = throw IllegalStateException("Unwrapped an error value")

        override fun unwrapChangeset() = unwrap()
    }
}

typealias MApiResult<T> = MResult<T, MTDResponseBody.Status>