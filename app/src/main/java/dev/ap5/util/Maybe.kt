package dev.ap5.util

sealed class Maybe<out T> {
    data class Some<T>(val value: T) : Maybe<T>()
    data object None : Maybe<Nothing>()
}