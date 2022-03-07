package android.example.githubuser.UI.Main.Main

import android.example.githubuser.MainRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GitHubUserListViewModel::class.java)) {
            GitHubUserListViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Не Нашлось")
        }
    }
}