package android.example.myanimelist.UI.Search

import android.example.myanimelist.Network.AnimeRetrofit
import android.example.myanimelist.Network.Resource
import android.example.myanimelist.R
import android.example.myanimelist.UI.Adapter.TopAnimeRecyclerViewAdapter
import android.example.myanimelist.UI.MainActivity
import android.example.myanimelist.UI.ViewModel.AnimeViewModel
import android.example.myanimelist.UI.ViewModel.MainRepository
import android.example.myanimelist.UI.ViewModel.MyViewModelFactory
import android.example.myanimelist.databinding.FragmentHomeTopAnimeListBinding
import android.example.myanimelist.databinding.FragmentSearchAnimesBinding
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchAnimeFragment : Fragment(R.layout.fragment_search_animes) {
    private var retrofit = AnimeRetrofit.getData()

    val TAG = "MARS" // Mars is hero from d o t a , one in favorite hero ))
    val viewModel : AnimeViewModel by lazy {
        ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofit))).get(
            AnimeViewModel::class.java
        )
    }

    lateinit var adapterSearchAnime : TopAnimeRecyclerViewAdapter
    private var searchAnimesBinding: FragmentSearchAnimesBinding? = null
    private val binding: FragmentSearchAnimesBinding
        get() = searchAnimesBinding!!


    private var currentPage = 1
    private var totalAvailblePages: Int = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = (activity as MainActivity).viewModel
        searchAnimesBinding = FragmentSearchAnimesBinding.bind(view)

        var job: Job? = null
        binding.etSearch.addTextChangedListener {
            var job: Job? = null
            binding.etSearch.addTextChangedListener { editable ->
                job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    editable?.let {
                        if(editable.toString().isNotEmpty()) {
                            viewModel.getSearchList(editable.toString()) //barlik parametri salu kerek
                        }
                    }
                }
            }}

        viewModel.searchAnimesLiveData.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { animeResponse ->
                        adapterSearchAnime.differ.submitList(animeResponse?.data)
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
        adapterSearchAnime = TopAnimeRecyclerViewAdapter()
        binding.rvSearchAnimes.apply {
            this.adapter = adapterSearchAnime
            this.layoutManager = LinearLayoutManager(activity)
        }

        binding.rvSearchAnimes.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            @Override
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rvSearchAnimes.canScrollVertically(1)){
                    if (currentPage <= totalAvailblePages){
                        currentPage++
                    }
                }
            }
        })
    }
}