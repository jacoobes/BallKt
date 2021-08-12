package dev.seren.serializables.player

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.seren.serializables.baseSerializable
import dev.seren.serializables.meta.MetaData
import dev.seren.serializables.team.TeamData
import java.io.Reader


data class PlayerData(
    val id : Int,
    val first_name: String,
    val last_name: String,
    val position: String,
    val height_feet: Int? = null,
    val height_inches: Int? = null,
    val weight_pounds: Int? = null,
    val team : TeamData

) : baseSerializable() {


    class Deserializer : ResponseDeserializable<PlayerData> {
        override fun deserialize(reader: Reader): PlayerData = Gson().fromJson(reader, PlayerData::class.java)!!
    }
    class ListDeserializer: ResponseDeserializable<PlayerDataList> {
        override fun deserialize(reader: Reader): PlayerDataList? {
            val type = object : TypeToken<PlayerDataList>() {}.type
            return Gson().fromJson(reader, type)
        }
    }

}
data class PlayerDataList(
    val data: List<PlayerData>,
    val meta: MetaData
)