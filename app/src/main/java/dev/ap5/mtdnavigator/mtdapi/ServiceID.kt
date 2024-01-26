package dev.ap5.mtdnavigator.mtdapi

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class ServiceID(private val id : String)