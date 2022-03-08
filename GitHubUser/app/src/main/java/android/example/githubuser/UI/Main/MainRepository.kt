package android.example.githubuser.UI.Main

import android.example.githubuser.Network.APIUserRequest

class MainRepository constructor(private val retrofit: APIUserRequest) {
    fun getAllUser() = retrofit.getUserList(10,"latestUserId")
    fun getUserDetail(id : Int) = retrofit.getUserDetail(id)
}