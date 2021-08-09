package dev.seren.serializables.meta

import dev.seren.serializables.baseSerializable
import kotlinx.serialization.Serializable




data class MetaData(
    val total_pages : Int,
    val current_page: Int,
    val next_page: Int?,
    val per_page: Int,
    val total_count: Int
) : baseSerializable()