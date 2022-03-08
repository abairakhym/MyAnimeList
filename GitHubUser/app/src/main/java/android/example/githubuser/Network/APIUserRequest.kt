package android.example.githubuser.Network

import android.example.githubuser.Model.DetailUser
import android.example.githubuser.Model.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIUserRequest {
    //https://api.github.com/users?per_page=10&since={latestUserId}
    @GET("users")
    fun getUserList(
        @Query("per_page") per_page : Int,
        @Query("since") since : String
    ) : Call <List<UserItem>>

    //https://api.github.com/user/{id}
    @GET("user/{id}")
    fun getUserDetail(
        @Path("id") id : Int
    ) : Call <DetailUser>
}