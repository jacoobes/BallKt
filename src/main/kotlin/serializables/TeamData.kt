package dev.seren.serializables.team

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.seren.Managers.Team
import dev.seren.serializables.baseSerializable
import dev.seren.serializables.meta.MetaData
import dev.seren.serializables.player.PlayerData
import dev.seren.serializables.player.PlayerDataList
import kotlinx.serialization.Serializable
import java.io.Reader


data class TeamData(
    val id: Int,
    val abbreviation : String,
    val city:String,
    val conference : String,
    val division: String,
    val full_name : String,
    val name : String
): baseSerializable() {

    class Deserializer : ResponseDeserializable<TeamData> {
        override fun deserialize(reader: Reader): TeamData = Gson().fromJson(reader, TeamData::class.java)!!
    }
    class ListDeserializer: ResponseDeserializable<TeamDataList> {
        override fun deserialize(reader: Reader): TeamDataList? {
            val type = object : TypeToken<TeamDataList>() {}.type
            return Gson().fromJson(reader, type)
        }
    }


}
data class TeamDataList(val data: List<TeamData>, val meta: MetaData)

