package com.example.fruticion.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fruticion.fragments.SearchFragment
import com.example.fruticion.model.Fruit
import com.example.fruticion.R
import com.example.fruticion.databinding.ActivityHomeBinding
import com.example.fruticion.fragments.InfoFragment
import com.example.fruticion.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), SearchFragment.OnFruitsLoadedListener {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var bottomNavigationView: BottomNavigationView
    private val navController by lazy {

        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var originalFruitList: List<Fruit> = emptyList()

    //Esto es para hacer HomeActivity.start() desde cualquier punto del codigo para iniciar HomeActivity en lugar de crearte la intent donde sea necesario
    companion object {
        fun start(
            context: Context,
        ) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)

        //codigo del lab de toolbar
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.searchFragment,
                R.id.favoriteFragment,
                R.id.profileFragment
            )
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Hide toolbar and bottom navigation when in detail fragment. (CODIGO LAB03 PREFERENCES)
       /* navController.addOnDestinationChangedListener { _, destination, _ ->
            if ((destination.id == R.id.detailFragment) ||
                    (destination.id == R.id.settingsFragment)) {
                binding.toolbar.menu.clear()
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.toolbar.visibility = View.VISIBLE
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }*/
    }

    override fun onStart() {
        super.onStart()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if ((destination.id == R.id.detailFragment) ||
                (destination.id == R.id.settingsFragment) ) {
                binding.toolbar.menu.clear()
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.toolbar.visibility = View.VISIBLE
                binding.bottomNavigationView.visibility = View.VISIBLE
               // setSupportActionBar(binding.toolbar)
            }
        }
    }


    //Este metodo se encarga de viajar a la actividad de detalles de la fruta pinchada
    /*override fun onShowClick(fruit: Fruit) {
        // Aquí puedes manejar la navegación a la actividad de Detalles o cualquier otra acción que desees realizar.
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("fruit", fruit)
        startActivity(intent)
    }*/


    //Metodos ToolBar

    //Este metodo controla la flecha Up que aparece cuando pinchas la lupa
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Realiza la acción cuando se envía la búsqueda (por ejemplo, inicia la búsqueda)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty().trim()
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                val searchFragment = navHostFragment.childFragmentManager.fragments.firstOrNull { it is SearchFragment } as SearchFragment?

                if (searchFragment != null) {
                    if (query.isNotEmpty()) {
                        // Filtra las frutas basadas en el texto de búsqueda en minúsculas
                        val filteredFruits = originalFruitList.filter { fruit ->
                            fruit.name.toString().lowercase().contains(query)
                        }

                        // Actualiza la lista de frutas en SearchFragment
                        searchFragment?.updateRecyclerView(filteredFruits)
                    } else {
                        // Si la búsqueda está vacía, muestra todas las frutas
                        searchFragment?.updateRecyclerView(originalFruitList)
                    }
                }

                return true
            }


        })

        return super.onCreateOptionsMenu(menu)
    }

    //Si se pulsa el icono de info se navega a InfoFragment
    override fun onOptionsItemSelected(item: MenuItem) :Boolean /*= when (item.itemId)*/ {
        /*R.id.action_info -> { // User chooses the "Settings" item. Show the app settings UI.
            Toast.makeText(this, "Info de la app", Toast.LENGTH_SHORT).show()

            navController.navigate(R.id.infoFragment)

            true
        }

        else -> { // The user's action isn't recognized. // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }*/

        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)

    }

    override fun onFruitsLoaded(fruits: List<Fruit>) {
        // Llamado cuando los datos de frutas se cargan en SearchFragment
        originalFruitList = fruits

    }


}
