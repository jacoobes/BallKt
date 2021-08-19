package dev.seren.serializables

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader


data class SeasonAvgData(
 val games_played : Int,
 val player_id :Int,
 val season :Int,
 val min : String,
 val fgm : Double,
 val fga : Double,
 val fg3m : Double,
 val fg3a : Double ,
 val ftm : Double,
 val fta : Double,
 val oreb : Double,
 val dreb : Double,
 val reb : Double,
 val ast : Double,
 val stl : Double,
 val blk : Double,
 val turnover :  Double,
 val pf :  Double,
 val pts : Double,
 val fg_pct : Double,
 val fg3_pct : Double,
 val ft_pct :  Double

) {

 class Deserialize: ResponseDeserializable<SeasonAvgData> {
  override fun deserialize(reader: Reader): SeasonAvgData = Gson().fromJson(reader, SeasonAvgData::class.java)


 }
 class ListDeserialize : ResponseDeserializable<SeasonAvgDataList> {
  override fun deserialize(reader: Reader): SeasonAvgDataList {
   val type =  object : TypeToken<SeasonAvgDataList>() {}.type
   return Gson().fromJson(reader, type)!!
  }

 }



}
data class SeasonAvgDataList(val data: List<SeasonAvgData>)
