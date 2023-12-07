package com.example.fruticion.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fruticion.R
import com.example.fruticion.databinding.FragmentIntakeBinding
import com.example.fruticion.view.adapters.TabPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class IntakeFragment : Fragment() {

    private var _binding: FragmentIntakeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntakeBinding.inflate(inflater, container, false)

        val pagerAdapter = TabPagerAdapter(this)
        binding.contentTabLayout.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.contentTabLayout) { tab, position ->
            // Aquí puedes personalizar el texto de las pestañas si es necesario
            if(position==0)
                tab.text = getText(R.string.bottom_daily)
            else
                tab.text = getText(R.string.bottom_weekly)
        }.attach()

        return binding.root
    }

}