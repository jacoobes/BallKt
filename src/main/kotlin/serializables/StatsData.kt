package dev.seren.serializables

import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import dev.seren.serializables.game.GameData
import dev.seren.serializables.meta.MetaData
import dev.seren.serializables.team.TeamData
import java.io.Reader

data class StatsData(
    val id: Int,
    val ast: Int,
    val blk: Int,
    val dreb: Int,
    val fg3_pct: Double,
    val fg3a: Int,
    val fg3m: Int,
    val fg_pct: Double,
    val fga: Int,
    val fgm: Int,
    val ft_pct: Double,
    val fta: Int,
    val ftm: Int,
    val game : GameData,
    val min: String,
    val oreb: Int,
    val pf: Int,
    val player : PlayerStatsData,
    val pts : Int,
    val reb: Int,
    val stl: Int,
    val team : TeamData,
    val turnover: Int
) {

    class StatsDataDeserializer() : ResponseDeserializable<StatsData> {
        override fun deserialize(reader: Reader): StatsData = Gson().fromJson(reader, StatsData::class.java)
    }

    class StatsListDeserializer() : ResponseDeserializable<ListStats> {
        override fun deserialize(reader: Reader): ListStats = Gson().fromJson(reader, ListStats::class.java)
    }


}

/**
 * smaller version of PlayerData, for stats serializable
 */
data class PlayerStatsData(
    val id  : Int,
    val first_name :String,
    val last_name : String,
    val position : String,
    val team_id : Int
)

data class ListStats (
    val data: List<StatsData>,
    val meta: MetaData
)












