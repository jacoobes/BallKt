package dev.seren.serializables.team

import dev.seren.serializables.baseSerializable
import kotlinx.serialization.Serializable


data class TeamData(
    val id: Int,
    val abbreviation : String,
    val city:String,
    val conference : String,
    val division: String,
    val full_name : String,
    val name : String
): baseSerializable()


