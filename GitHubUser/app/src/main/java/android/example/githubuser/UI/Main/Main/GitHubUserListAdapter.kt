package android.example.githubuser.UI.Main.Main

import android.example.githubuser.R
import android.example.githubuser.Model.UserItem
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GitHubUserListAdapter(): RecyclerView.Adapter<GitHubUserListAdapter.GitHubUserViewHolder>() {

    var users = mutableListOf<UserItem>()
    fun setUserList(users: List<UserItem>) {
        this.users = users.toMutableList()
        notifyDataSetChanged()
    }

    class GitHubUserViewHolder(val itemView :View): RecyclerView.ViewHolder(itemView) {
        val tvLogin = itemView.findViewById<TextView>(R.id.tvLogin)
        val tvId = itemView.findViewById<TextView>(R.id.tvId)
        val ivAvatar = itemView.findViewById<ImageView>(R.id.ivAvatar)

        fun bind(data: UserItem){
            tvLogin.text = data.login
            tvId.text = data.id.toString()

            Glide.with(itemView.context).load(data.avatar_url).centerCrop().into(ivAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubUserViewHolder {
        return GitHubUserViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item,parent,false))
    }

    override fun onBindViewHolder(holder: GitHubUserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        Log.d("usersSize","result = ${users.size}")
        return users.size
    }
}