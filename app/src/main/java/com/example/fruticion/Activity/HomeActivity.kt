package com.example.fruticion.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fruticion.Fragments.SearchFragment
import com.example.fruticion.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Cargar el fragmento inicial
        loadFragment(SearchFragment())

        // Manejar clics en la barra de navegación inferior
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> loadFragment(SearchFragment())
               // R.id.navigation_fav -> loadFragment(FragmentFavorites())
                //R.id.navigation_profile -> loadFragment(FragmentProfile())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
