package android.example.myanimelist.Network

import android.example.myanimelist.Model.Anime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApi {

    //https://api.jikan.moe/v4/top/anime?page=2
    @GET("top/anime")
    suspend fun getAnimeList(  //top anime list
        @Query("page") page : Int
    ) : Response<Anime>

    //https://api.jikan.moe/v4/anime?page=1&q&type&score&genres=1,2&order_by=score
    //https://api.jikan.moe/v4/anime
    @GET("anime")
    suspend fun getSearchAnimeList(   //ye i know this bad name for this function
        @Query("q") q : String, //name anime
        @Query("page") page : Int,
        @Query("type") type : List<String>,
        @Query("min_score") min_score : Double,
        @Query("max_score") max_score : Double,
        @Query("status") status : String, //List<String>
        @Query("rating") rating : List<String>,
        @Query("genres") genres : List<Int>,
        @Query("order_by") order_by : List<String>,
        @Query("sfw") sfw : Boolean = true // Filter out Adult entries
    ) : Response<Anime>

    @GET("anime")
    suspend fun seasonalAnimes(   //ye i know this bad name for this function
        @Query("status") status : String, //List<String>
        @Query("page") page : Int,
        @Query("sfw") sfw : Boolean = true // Filter out Adult entries
    ) : Response<Anime>
}