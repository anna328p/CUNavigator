package dev.ap5.mtdapi.rest.misc

import dev.ap5.mtdapi.ids.ChangesetID
import dev.ap5.mtdapi.rest.responses.MTDResponseBody
import kotlin.jvm.Throws

sealed interface MResult<out A, out B> {
    @Throws(IllegalStateException::class)
    fun unwrap() : A?

    data class Ok<A>(val value : A, val changesetID: ChangesetID? = null) : MResult<A, Nothing> {
        override fun unwrap() : A = value
    }

    data class Err<B>(val value : B) : MResult<Nothing, B> {
        override fun unwrap() = throw IllegalStateException("Unwrapped an error value")
    }

    data class NotModified(val changesetID: ChangesetID? = null) : MResult<Nothing, Nothing> {
        override fun unwrap(): Nothing? = null
    }
}

typealias MApiResult<T> = MResult<T, MTDResponseBody.Status>