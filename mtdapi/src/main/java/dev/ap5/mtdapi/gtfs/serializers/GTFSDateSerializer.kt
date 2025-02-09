package dev.ap5.mtdapi.gtfs.serializers

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object GTFSDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("kotlinx.datetime.LocalDate", PrimitiveKind.STRING)

    private val dateFormat = LocalDate.Formats.ISO_BASIC

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(dateFormat))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return dateFormat.parse(decoder.decodeString())
    }
}