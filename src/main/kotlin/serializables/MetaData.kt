package dev.seren.serializables.meta


data class MetaData(
    val total_pages : Int,
    val current_page: Int,
    val next_page: Int?,
    val per_page: Int,
    val total_count: Int
)