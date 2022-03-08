package android.example.githubuser.UI.Main.Main

import android.example.githubuser.UI.Main.MainRepository
import android.example.githubuser.Model.UserItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitHubUserListViewModel constructor(private val repository: MainRepository) : ViewModel() {
    var userList = MutableLiveData<List<UserItem>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllUsers() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = repository.getAllUser()
            response.enqueue(object : Callback<List<UserItem>> {
                override fun onResponse(
                    call: Call<List<UserItem>>,
                    response: Response<List<UserItem>>
                ) {
                    userList.postValue(response.body())
                }
                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }
            })
        }
    }
}


