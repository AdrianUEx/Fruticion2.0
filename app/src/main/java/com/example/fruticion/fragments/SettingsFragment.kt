package com.example.fruticion.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.fruticion.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}