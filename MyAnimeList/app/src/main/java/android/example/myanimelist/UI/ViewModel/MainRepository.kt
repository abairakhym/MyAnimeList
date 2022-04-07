package android.example.myanimelist.UI.ViewModel

import android.example.myanimelist.Network.AnimeApi

class MainRepository constructor(
    private val retrofit: AnimeApi) {

    suspend fun getTopAnimes(
        page : Int
    ) = retrofit.getAnimeList(page)

    suspend fun searchAnimes(
        q : String,
        page : Int,
        type : List<String>,
        min_score : Double,
        max_score : Double,
        status : String, //List<String>
        rating : List<String>,
        genres : List<Int>,
        order_by : List<String>
    ) = retrofit.getSearchAnimeList(q, type, min_score, max_score, status, rating, genres, order_by)

    suspend fun seasonalAnimes(
        status : String
    ) = retrofit.seasonalAnimes(status)
}
