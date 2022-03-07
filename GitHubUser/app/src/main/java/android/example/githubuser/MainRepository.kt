package android.example.githubuser

import android.example.githubuser.Network.APIUserRequest

class MainRepository constructor(private val retrofit: APIUserRequest) {
    fun getAllUser() = retrofit.getUserList(10,"latestUserId")
}