package android.example.githubuser.UI.Main.Detail

import android.example.githubuser.UI.Main.MainRepository
import android.util.Log
import android.example.githubuser.Model.UserItem
import android.example.githubuser.Network.RetrofitInstanse
import android.example.githubuser.R
import android.example.githubuser.UI.Main.MyViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private val TAG = "MainJUGGERNAUT"
    private val EXTRA_INTENT_KEY = "AXE"

    lateinit var viewModel : DetailUserViewModel
    private var retrofit = RetrofitInstanse.getInstanse()
    private var id : Int? = null
    private lateinit var avatar: ImageView
    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var organization: TextView
    private lateinit var following: TextView
    private lateinit var followers: TextView
    private lateinit var cData: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val bundle: Bundle? = intent.extras
        id = bundle?.getInt(EXTRA_INTENT_KEY)
        avatar = findViewById(R.id.ivDetail)
        name = findViewById(R.id.tvNameDetail)
        email = findViewById(R.id.tvEmail)
        organization = findViewById(R.id.tvOrganization)
        following = findViewById(R.id.tvFollowing)
        followers = findViewById(R.id.tvFollowers)
        cData = findViewById(R.id.tvCreationData)

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofit))).get(
            DetailUserViewModel::class.java
        )
        setUI()
    }

    private fun setUI() {
        viewModel.setUserDetail(id!!)
        viewModel.errorMessage.observe(this@DetailUserActivity, Observer {
        })
        viewModel.getUserDetail().observe(this@DetailUserActivity, {
            if (it != null) {
                name.setText(it.name)
                email.setText("Email : " + it.email)
                following.setText("Following : " + it.following)
                followers.setText("Followers : " + it.followers)
                cData.setText("Created : " + it.created_at)
                organization.setText(it.company)

                Glide.with(this@DetailUserActivity)
                    .load(it.avatar_url)
                    .centerCrop()
                    .into(avatar)
            }
        })
    }
}