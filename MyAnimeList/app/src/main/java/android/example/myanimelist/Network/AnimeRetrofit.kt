package android.example.myanimelist.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AnimeRetrofit {

    companion object {
        val BASE_URL = "https://api.jikan.moe/v4/"

        var apiRequest : AnimeApi? = null

        fun getData(): AnimeApi {
            if (apiRequest == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory
                        .create())
                    .build()
                apiRequest = retrofit.create(AnimeApi::class.java)
            }
            return apiRequest!!
        }
    }
}