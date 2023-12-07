package com.example.fruticion.view.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fruticion.view.fragments.DailyIntakeFragment
import com.example.fruticion.view.fragments.IntakeFragment
import com.example.fruticion.view.fragments.WeeklyIntakeFragment

class TabPagerAdapter(fragmentActivity: IntakeFragment) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DailyIntakeFragment()
            1 -> WeeklyIntakeFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getItemCount(): Int {
        return 2 // Número total de fragmentos
    }
}