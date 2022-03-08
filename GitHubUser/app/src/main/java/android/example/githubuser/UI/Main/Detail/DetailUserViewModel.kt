package android.example.githubuser.UI.Main.Detail

import android.example.githubuser.UI.Main.MainRepository
import android.example.githubuser.Model.DetailUser
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel constructor(private val repository: MainRepository) : ViewModel() {
    var user = MutableLiveData<DetailUser>()
    val errorMessage = MutableLiveData<String>()

    fun setUserDetail(id : Int){
        GlobalScope.launch(Dispatchers.IO) {
            val response = repository.getUserDetail(id)
            response.enqueue(object : Callback<DetailUser>{
                override fun onResponse(
                    call: Call<DetailUser>,
                    response: Response<DetailUser>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }
                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }
            })
        }
    }

    fun getUserDetail() : MutableLiveData<DetailUser>{
        return user
    }
}