package android.example.myanimelist.UI.Adapter

import android.example.myanimelist.UI.Seasonal.AiringFragment
import android.example.myanimelist.UI.Seasonal.CompleteFragment
import android.example.myanimelist.UI.Seasonal.UpcomingFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SeasonalViewPager (fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                CompleteFragment()
            }
            1->{
                AiringFragment()
            }
            2->{
                UpcomingFragment()
            }
            else->{
                UpcomingFragment()
            }

        }
    }
}