package android.example.myanimelist.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory constructor(
    private val repository : MainRepository,
    private val status : String = "upcoming"
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AnimeViewModel::class.java)){
            AnimeViewModel(this.repository , status) as T
        } else {
            throw IllegalArgumentException("ViewModel not found(Не нашлось)")
        }
    }

}