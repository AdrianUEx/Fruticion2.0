package com.example.fruticion.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fruticion.R
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruticion.Activity.HomeActivity
import com.example.fruticion.Data.dummyFruit
import com.example.fruticion.Model.Fruit
import com.example.fruticion.databinding.FragmentSearchBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment()   {

    interface OnShowClickListener {
        fun onShowClick(fruit: Fruit)
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    public var homeActivity: HomeActivity? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.rvFruitList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = SearchAdapter(dummyFruit) { fruit -> onItemSelected(fruit) }
        
    }

    fun onItemSelected(fruit: Fruit) {
        (requireActivity() as OnShowClickListener).onShowClick(fruit)
    }


}