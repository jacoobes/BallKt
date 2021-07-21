package dev.seren.serializables.player

import dev.seren.serializables.baseSerializable
import dev.seren.serializables.meta.MetaData
import dev.seren.serializables.team.TeamData
import kotlinx.serialization.Serializable



@Serializable
data class PlayerData(
    val id : Int,
    val first_name: String,
    val last_name: String,
    val position: String,
    val height_feet: Int?,
    val height_inches: Int?,
    val weight_pounds: Int?,
    val team : TeamData

) : baseSerializable()


@Serializable
data class PlayerList(
    val data: List<PlayerData>,
    val meta: MetaData
)