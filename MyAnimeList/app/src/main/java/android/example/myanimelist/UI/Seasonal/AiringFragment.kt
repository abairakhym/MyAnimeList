package android.example.myanimelist.UI.Seasonal

import android.example.myanimelist.Network.AnimeRetrofit
import android.example.myanimelist.Network.Resource
import androidx.fragment.app.Fragment
import android.example.myanimelist.R
import android.example.myanimelist.UI.Adapter.SeasonalRecyclerViewAdapter
import android.example.myanimelist.UI.ViewModel.AnimeViewModel
import android.example.myanimelist.UI.ViewModel.MainRepository
import android.example.myanimelist.UI.ViewModel.MyViewModelFactory
import android.example.myanimelist.databinding.FragmentAiringBinding
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AiringFragment : Fragment(R.layout.fragment_airing) {
    private var retrofit = AnimeRetrofit.getData()

    val TAG = "MARS" // Mars is hero from d o t a , one in favorite hero ))
    private var status : String = "airing"

    val viewModel : AnimeViewModel by lazy {
        ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofit),status)).get(
            AnimeViewModel::class.java
        )
    }

    lateinit var adapterSeasonalAnime : SeasonalRecyclerViewAdapter
    private var seasonalBinding: FragmentAiringBinding? = null
    private val binding: FragmentAiringBinding    ///aystiru kerek
        get() = seasonalBinding!!

    private var currentPage = 1
    private var totalAvailblePages: Int = 1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seasonalBinding = FragmentAiringBinding.bind(view)


        viewModel.seasonalLiveData.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { animeResponse ->
                        adapterSeasonalAnime.differ.submitList(animeResponse?.data)
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
        adapterSeasonalAnime = SeasonalRecyclerViewAdapter()
        binding.rvList.apply {
            this.adapter = adapterSeasonalAnime
            this.layoutManager = GridLayoutManager(requireContext(),2)
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