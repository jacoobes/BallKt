package dev.seren.serializables

import kotlinx.serialization.Serializable

@Serializable
data class SeasonAvgData(
 val games_played : Int,
 val player_id :Int,
 val season :Int,
 val min : Int,
 val fgm : Int,
 val fga : Int,
 val fg3m : Int,
 val fg3a :Int ,
 val ftm :Int ,
 val fta :Int,
 val oreb :Int,
 val dreb :Int,
 val reb :Int,
 val ast :Int,
 val stl :Int,
 val blk :Int,
 val turnover :Int,
 val pf :Int,
 val pts :Int,
 val fg_pct :Int,
 val fg3_pct :Int,
 val ft_pct : Int

)
