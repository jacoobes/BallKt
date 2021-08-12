package dev.seren.serializables.game
import dev.seren.serializables.baseSerializable
import dev.seren.serializables.team.TeamData
import kotlinx.serialization.Serializable




data class GameData(
    val id : Int,
    val date : String,
    val home_team_score : Int,
    val visitor_team_score : Int,
    val season : Int,
    val period : Int,
    val status : String,
    val time: String,
    val postseason : Boolean,
    val home_team : TeamData,
    val visitor_team : TeamData

): baseSerializable()


