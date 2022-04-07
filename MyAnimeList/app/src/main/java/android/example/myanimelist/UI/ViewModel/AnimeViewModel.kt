package android.example.myanimelist.UI.ViewModel

import android.example.myanimelist.Model.Anime
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.example.myanimelist.Network.Resource
import retrofit2.Response


class AnimeViewModel constructor(val repository: MainRepository, val status: String = "upcoming") : ViewModel() {
    //search
    val searchAnimesLiveData: MutableLiveData<Resource<Anime>> = MutableLiveData()
    var searchAnimePage = 1

    //seasonal
    val seasonalLiveData : MutableLiveData<Resource<Anime>> = MutableLiveData()
    var seasonalAnimePage = 1

    //top
    val topAnimeLiveData : MutableLiveData<Resource<Anime>> = MutableLiveData()
    var topAnimePage = 1

    init {
        getTopAnimeList(topAnimePage)
        getSeasonalList(status, seasonalAnimePage) // nado izmenit chtob poluchat vihodasir i upcoming
    }

    //top
    fun getTopAnimeList(page : Int) = viewModelScope.launch {
        topAnimeLiveData.postValue(Resource.Loading())
        val response = repository.getTopAnimes(page)
        topAnimeLiveData.postValue(handleTopAnimesResponse(response))
    }
    //top
    private fun handleTopAnimesResponse(response: Response<Anime>) : Resource<Anime>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    //seasonal
    fun getSeasonalList(status: String, page : Int) = viewModelScope.launch {
        seasonalLiveData.postValue(Resource.Loading())
        val response = repository.seasonalAnimes(status)
        seasonalLiveData.postValue(handleSeasonalAnimesResponse(response))
    }
    //seasonal
    private fun handleSeasonalAnimesResponse(response: Response<Anime>) : Resource<Anime>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    //search
    fun getSearchList(q : String,
                      page : Int,
                      type : List<String>,
                      min_score : Double,
                      max_score : Double,
                      status : String, //List<String>
                      rating : List<String>,
                      genres : List<Int>,
                      order_by : List<String>) = viewModelScope.launch {
        searchAnimesLiveData.postValue(Resource.Loading())
        val response = repository.searchAnimes(q, page, type, min_score, max_score, status, rating, genres, order_by)
        searchAnimesLiveData.postValue(handleSearchAnimesResponse(response))
    }
    //search
    private fun handleSearchAnimesResponse(response: Response<Anime>) : Resource<Anime>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}

