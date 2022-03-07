package android.example.githubuser.UI.Main.Main

import android.example.githubuser.MainRepository
import android.example.githubuser.Network.RetrofitInstanse
import android.example.githubuser.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val TAG = "MainMARS"

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

    lateinit var viewModel: GitHubUserListViewModel
    private var retrofitService = RetrofitInstanse.getInstanse()
    lateinit var adapter : GitHubUserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
            GitHubUserListViewModel::class.java)

        adapter = GitHubUserListAdapter()
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter

        viewModel.userList.observe(this, Observer {
            Log.d(TAG, "result = $it")
            if(it != null) {
                adapter.setUserList(it)
            }
        })
        viewModel.errorMessage.observe(this, Observer {
        })
        viewModel.getAllUsers()
    }
}