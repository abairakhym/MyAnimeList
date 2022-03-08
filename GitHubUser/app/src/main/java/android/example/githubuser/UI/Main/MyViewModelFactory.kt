package android.example.githubuser.UI.Main

import android.example.githubuser.UI.Main.Detail.DetailUserViewModel
import android.example.githubuser.UI.Main.Main.GitHubUserListViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GitHubUserListViewModel::class.java)) {
            GitHubUserListViewModel(this.repository) as T
        } else if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)){
            DetailUserViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Не Нашлось")
        }
    }
}