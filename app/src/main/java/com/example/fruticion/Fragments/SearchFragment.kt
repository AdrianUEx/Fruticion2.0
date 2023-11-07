package com.example.fruticion.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruticion.Activity.HomeActivity
import com.example.fruticion.api.APIError
import com.example.fruticion.api.SerializedFruit
import com.example.fruticion.api.getNetworkService
//import com.example.fruticion.dummy.dummyFruit
import com.example.fruticion.model.Fruit
import com.example.fruticion.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

/*
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
*/



class SearchFragment : Fragment()   {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    var homeActivity: HomeActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val searchView = binding.searchView

        // Configurar escucha de cambios en la barra de búsqueda
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Realizar acción cuando se envía la búsqueda (por ejemplo, iniciar búsqueda)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Realizar acción cuando se cambia el texto en la barra de búsqueda
                return true
            }
        })

        return binding.root
    }

    //Este metodo actua despues de que el Fragment ya se ha creado, para añadir cosas extra al Fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch{
            var fruits: List<Fruit> = fetchAllFruits()

            setUpRecyclerView(fruits)
        }


    }
    private suspend fun fetchAllFruits(): List<Fruit> {
        var fruitList = listOf<Fruit>()
        try {
           fruitList = getNetworkService().getAllFruits()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
        return fruitList
    }
    private fun setUpRecyclerView(fruits: List<Fruit>) {
        val recyclerView = binding.rvFruitList //Linkea la RecyclerView del layout con ViewBinding en esta variable
        recyclerView.layoutManager = LinearLayoutManager(context) //Configura el layoutManager de la RecyclerView para que adopte una configuracion vertical en lugar de en grid
        //TODO:cambiar de vuelta a dummyFruits si no funciona
        recyclerView.adapter = SearchAdapter(fruits) { fruit -> onItemSelected(fruit) }
    }

    //
    fun onItemSelected(fruit: Fruit) {
        (requireActivity() as OnShowClickListener).onShowClick(fruit)//En esta linea se esta recuperando la Activity a la que pertenece este Fragment para invocar al override de onShowClick() alli definido
        //requireActivity() obtiene la Activity de este Fragment. Hace un Casting de OnShowClickListener, por tanto, se "asume" que la HomeActivity debe implementar OnShowClickListener o si no, lanzara una excepcion
    }
    interface OnShowClickListener {
        fun onShowClick(fruit: Fruit)//Esta funcion es overrideada en HomeActivity para lanzar una Intent para viajar a la pantalla de detalle de la fruta pinchada
    }

}