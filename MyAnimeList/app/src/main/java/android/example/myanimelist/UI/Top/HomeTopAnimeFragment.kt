package android.example.myanimelist.UI.Top

import android.example.myanimelist.Network.AnimeRetrofit
import android.example.myanimelist.Network.Resource
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.example.myanimelist.R
import android.example.myanimelist.UI.Adapter.TopAnimeRecyclerViewAdapter
import android.example.myanimelist.UI.ViewModel.AnimeViewModel
import android.example.myanimelist.UI.ViewModel.MainRepository
import android.example.myanimelist.UI.ViewModel.MyViewModelFactory
import android.example.myanimelist.databinding.FragmentHomeTopAnimeListBinding
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class HomeTopAnimeFragment : Fragment(R.layout.fragment_home_top_anime_list) {

    private var retrofit = AnimeRetrofit.getData()

    val TAG = "MARS" // Mars is hero from d o t a , one in favorite hero ))

    val viewModel : AnimeViewModel by lazy {
        ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofit))).get(
            AnimeViewModel::class.java
        )
    }

    lateinit var adapterTopAnime : TopAnimeRecyclerViewAdapter
    private var topAnimesBinding: FragmentHomeTopAnimeListBinding? = null
    private val binding: FragmentHomeTopAnimeListBinding
        get() = topAnimesBinding!!

    private var currentPage = 1
    private var totalAvailblePages: Int = 1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topAnimesBinding = FragmentHomeTopAnimeListBinding.bind(view)

        viewModel.topAnimeLiveData.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { animeResponse ->
                        adapterTopAnime.differ.submitList(animeResponse?.data)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "error -->  $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        initRecyclerView()
    }

    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun initRecyclerView(){
        adapterTopAnime = TopAnimeRecyclerViewAdapter()
        binding.rvList.apply {
            this.adapter = adapterTopAnime
            this.layoutManager = LinearLayoutManager(activity)
        }

        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            @Override
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rvList.canScrollVertically(1)){
                    if (currentPage <= totalAvailblePages){
                        currentPage++
                    }
                }
            }
        })
    }

}

