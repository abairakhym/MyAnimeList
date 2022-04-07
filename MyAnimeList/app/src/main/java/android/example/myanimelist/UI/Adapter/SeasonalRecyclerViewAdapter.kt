package android.example.myanimelist.UI.Adapter

import android.example.myanimelist.Model.Data
import android.example.myanimelist.R
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SeasonalRecyclerViewAdapter
    : RecyclerView.Adapter<SeasonalRecyclerViewAdapter.SeasonalViewHolder>(){

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

    inner class SeasonalViewHolder(val itemView : View): RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.textViewTitle)
        val ivAvatar = itemView.findViewById<ImageView>(R.id.imageViewAnime)

        fun bind(data: Data){ //:Anime
            tvName.text = data.title

            Glide.with(itemView.context).load(data.images.jpg.image_url).centerCrop().into(ivAvatar)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonalViewHolder {
        return SeasonalViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.seasonal_anime_grid_list,parent,false)) //fragment_home_top_anime_list?
    }

    override fun onBindViewHolder(holder: SeasonalViewHolder, position: Int) {

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