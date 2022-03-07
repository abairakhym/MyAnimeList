package android.example.githubuser.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstanse {

    companion object {
        val BASE_URL = "https://api.github.com"

        var apiUserRequest : APIUserRequest? = null

        fun getInstanse(): APIUserRequest {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            apiUserRequest = retrofit.create(APIUserRequest::class.java)
            return apiUserRequest!!
        }
    }
}