package dev.seren.serializables.game
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.seren.Managers.Game
import dev.seren.serializables.baseSerializable
import dev.seren.serializables.meta.MetaData
import dev.seren.serializables.team.TeamData
import kotlinx.serialization.Serializable
import java.io.Reader


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

): baseSerializable() {

    class Deserializer() : ResponseDeserializable<GameData> {
        override fun deserialize(reader: Reader): GameData {
            return Gson().fromJson(reader, GameData::class.java)
        }
    }

    class ListDeserializer() : ResponseDeserializable<GameDataList> {
        override fun deserialize(reader: Reader): GameDataList {
            val type = object : TypeToken<GameDataList>() {}.type
            return Gson().fromJson(reader, type)
        }
    }

}

data class GameDataList(
    val data: List<GameData>,
    val meta : MetaData
)

