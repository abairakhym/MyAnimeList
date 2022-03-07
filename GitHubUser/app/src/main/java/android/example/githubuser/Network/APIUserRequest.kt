package android.example.githubuser.Network

import android.example.githubuser.Model.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIUserRequest {
    //https://api.github.com/users?per_page=10&since=%7BlatestUserId%7D
    @GET("users")
    fun getUserList(
        @Query("per_page") per_page : Int,
        @Query("since") since : String
    ) : Call <List<UserItem>>

}