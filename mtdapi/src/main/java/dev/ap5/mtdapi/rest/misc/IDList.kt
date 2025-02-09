package dev.ap5.mtdapi.rest.misc

import dev.ap5.mtdapi.ids.IDType

/**
 * A list of IDs.
 *
 * Exists as a workaround for Retrofit's inflexible handling of lists in query parameters.
 *
 * @param TID  type that implements [IDType] (see [dev.ap5.mtdapi.ids])
 *
 * @see retrofit2.ParameterHandler.iterable
 */
class IDList<TID : IDType>(vararg val ids : TID) {
    override fun toString() : String =
        ids.joinToString(separator = ";") { it.toString() }

    companion object {
        /**
         * Constructs an [IDList] from strings.
         *
         * @param TID      type that implements [IDType] (see [dev.ap5.mtdapi.ids]
         * @param strings  arguments
         */
        inline fun <reified TID : IDType> of(vararg strings : String) : IDList<TID> {
            val cons = TID::class.constructors.first()
            val ids = strings.map { cons.call(it) }

            return IDList(*ids.toTypedArray())
        }

        fun <ID : IDType> of(vararg ids : ID) = IDList(*ids)
    }

    override fun equals(other: Any?): Boolean {
        return other is IDList<*> && this.ids.contentEquals(other.ids)
    }

    override fun hashCode(): Int {
        return ids.contentHashCode()
    }
}