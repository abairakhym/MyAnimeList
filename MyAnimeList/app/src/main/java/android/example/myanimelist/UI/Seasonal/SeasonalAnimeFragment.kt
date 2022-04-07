package android.example.myanimelist.UI.Seasonal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.example.myanimelist.R
import android.example.myanimelist.UI.Adapter.SeasonalViewPager
import android.example.myanimelist.databinding.FragmentSeasonalAnimesBinding
import com.google.android.material.tabs.TabLayoutMediator

class SeasonalAnimeFragment : Fragment(R.layout.fragment_seasonal_animes) {

    private var seasonalBinding: FragmentSeasonalAnimesBinding? = null
    private val binding: FragmentSeasonalAnimesBinding     ///aystiru kerek
        get() = seasonalBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seasonalBinding = FragmentSeasonalAnimesBinding.bind(view)

        val adapter = SeasonalViewPager(parentFragmentManager,lifecycle)
        binding.viewPager2.adapter= adapter

        TabLayoutMediator(binding.tabLayout,binding.viewPager2){tab,position->
            when(position){
                0->{
                    tab.text="C"
                }
                1->{
                    tab.text="A"
                }
                2->{
                    tab.text="U"
                }
            }
        }.attach()

    }


}