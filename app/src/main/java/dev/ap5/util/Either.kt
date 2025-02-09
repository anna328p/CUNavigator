package dev.ap5.util

sealed class Either<out A, out B> {
    data class L<A, B>(val value : A) : Either<A, B>()
    data class R<A, B>(val value : B) : Either<A, B>()
}