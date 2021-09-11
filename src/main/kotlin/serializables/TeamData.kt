package dev.seren.serializables.team

import dev.seren.serializables.meta.MetaData


data class TeamData(
    val id: Int,
    val abbreviation : String,
    val city:String,
    val conference : String,
    val division: String,
    val full_name : String,
    val name : String
)

data class TeamDataList(val data: List<TeamData>, val meta: MetaData)

