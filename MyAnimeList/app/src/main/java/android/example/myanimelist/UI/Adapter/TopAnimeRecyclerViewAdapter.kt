package android.example.myanimelist.UI.Adapter

import android.example.myanimelist.Model.Data
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.example.myanimelist.R
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide

class TopAnimeRecyclerViewAdapter()
    : RecyclerView.Adapter<TopAnimeRecyclerViewAdapter.TopAnimeViewHolder>() {

    private var mListener: ((onItemClickListener) -> Unit)? = null

    private val differCallback = object : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.title == newItem.title //id?
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    interface onItemClickListener{
        fun onItemClick(data: Data) // : Anime
    }

    fun setOnItemClickListener(listener: ((onItemClickListener) -> Unit)? = null){
        mListener = listener
    }

    inner class TopAnimeViewHolder(val itemView :View): RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.textViewName)
        val ivAvatar = itemView.findViewById<ImageView>(R.id.imageView)

        fun bind(data: Data){ //:Anime
            tvName.text = data.title

            Glide.with(itemView.context).load(data.images.jpg.image_url).centerCrop().into(ivAvatar)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAnimeViewHolder {
        return TopAnimeViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_home_top_anime,parent,false)) //fragment_home_top_anime_list?
    }

    override fun onBindViewHolder(holder: TopAnimeViewHolder, position: Int) {

        val anime = differ.currentList[position]
        holder.bind(anime)

        holder.apply {

            setOnItemClickListener {
                //onItemClickListener.let {  }
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("Size","result = ${differ.currentList.size}")
        return differ.currentList.size
    }
}

