package android.example.githubuser.UI.Main.Main

import android.content.Intent
import android.example.githubuser.UI.Main.MainRepository
import android.example.githubuser.Model.UserItem
import android.example.githubuser.Network.RetrofitInstanse
import android.example.githubuser.R
import android.example.githubuser.UI.Main.Detail.DetailUserActivity
import android.example.githubuser.UI.Main.MyViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val TAG = "MainMARS"
    private val EXTRA_INTENT_KEY = "AXE"

    private lateinit var recyclerView : RecyclerView
    private lateinit var layoutManager : LinearLayoutManager

    lateinit var viewModel : GitHubUserListViewModel
    private var retrofit = RetrofitInstanse.getInstanse()
    lateinit var adapter : GitHubUserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofit))).get(
            GitHubUserListViewModel::class.java
        )

        adapter = GitHubUserListAdapter()
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter

        //click rv
        adapter.setOnItemClickListener(object: GitHubUserListAdapter.onItemClickListener{
            override fun onItemClick(data: UserItem) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(EXTRA_INTENT_KEY,data.id)
                    startActivity(it)
                }
            }
        })
        setUIMain()
    }
    private fun setUIMain() {
        GlobalScope.launch(Dispatchers.Main) {

            viewModel.userList.observe(this@MainActivity, Observer {
                Log.d(TAG, "result = $it")
                if (it != null) {
                    adapter.setUserList(it)
                }
            })
            viewModel.errorMessage.observe(this@MainActivity, Observer {
            })
            withContext(Dispatchers.IO) {
                viewModel.getAllUsers()
            }
        }
    }
}