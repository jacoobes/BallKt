package dev.seren.serializables.player

import dev.seren.serializables.meta.MetaData
import dev.seren.serializables.team.TeamData


data class PlayerData(
    val id : Int,
    val first_name: String,
    val last_name: String,
    val position: String,
    val height_feet: Int? = null,
    val height_inches: Int? = null,
    val weight_pounds: Int? = null,
    val team : TeamData

)
data class PlayerDataList(
    val data: List<PlayerData>,
    val meta: MetaData
)