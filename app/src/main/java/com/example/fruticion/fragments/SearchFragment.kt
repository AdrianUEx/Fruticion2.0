package com.example.fruticion.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruticion.api.APIError
import com.example.fruticion.api.FruitMapper
import com.example.fruticion.api.SerializedFruit
import com.example.fruticion.api.getNetworkService
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.model.Fruit
import com.example.fruticion.databinding.FragmentSearchBinding
import com.example.fruticion.fragments.adapters.SearchAdapter
import kotlinx.coroutines.launch

class SearchFragment : Fragment()   {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!! //Esta linea la tenia Roberto hecha asi en el lab en los Fragment, y por eso lo hemos hecho asi.

    private lateinit var searchAdapter: SearchAdapter
    private var onFruitsLoadedListener: OnFruitsLoadedListener? = null

    private lateinit var db: FruticionDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        //Se obtiene la instancia de la BD
        db = FruticionDatabase.getInstance(requireActivity().applicationContext)!!

        return binding.root
    }

    //Este metodo actua despues de que el Fragment ya se ha creado
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Se invoca a la API para cargar el fragment con las frutas al principio
        lifecycleScope.launch{
            //obtenemos TODAS las frutas de la API (45 frutas)
            val fruits: List<SerializedFruit> = fetchAllFruits()

            //Se usa el mapper para mapear correctamente las frutas en el modelo serializado al modelo interno de la aplicacion.
            val readyFruitList = FruitMapper.mapFromSerializedFruitList(fruits)
            db.fruitDao().addFruitList(readyFruitList)


            //Recuperamos de Room todas las frutas para meterlas por el RecyclerView
            val dbFruits = db.fruitDao().getAll()
            onFruitsLoadedListener?.onFruitsLoaded(dbFruits)
            setUpRecyclerView(dbFruits)
        }
    }

    //Este metodo solamente se encarga de llamar al getAllFruits() de FruticionAPI.
    private suspend fun fetchAllFruits(): List<SerializedFruit> {
        var fruitList = listOf<SerializedFruit>()
        try {
                fruitList = getNetworkService().getAllFruits()
        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
        return fruitList
    }

    //Este metodo es SOLO para evitar posibles fugas de memoria del Fragment.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }



//-----METODOS RECYCLER VIEW---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private fun setUpRecyclerView(fruits: List<Fruit>) {
        val recyclerView = binding.rvFruitList // Linkea la RecyclerView del layout con ViewBinding en esta variable
        recyclerView.layoutManager = LinearLayoutManager(context) // Configura el layoutManager de la RecyclerView para que adopte una configuración vertical en lugar de en grid

        // Inicializa searchAdapter con la lista de frutas
        searchAdapter = SearchAdapter(fruits) { fruit -> onItemSelected(fruit.roomId!!) }

        // Asigna el adaptador a la RecyclerView
        recyclerView.adapter = searchAdapter
    }

    //Este metodo usa la action definida en el grafo de navegación para viajar al detalle de la fruta seleccionada
    private fun onItemSelected(fruitId: Long) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(fruitId=fruitId))
    }

    interface OnFruitsLoadedListener {
        fun onFruitsLoaded(fruits: List<Fruit>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFruitsLoadedListener) {
            onFruitsLoadedListener = context
        }
    }

    //Este metodo SOLAMENTE es invocado desde HomeActivity
    fun updateRecyclerView(newData: List<Fruit>) {
        val modifiedData = ArrayList(newData)
        searchAdapter.updateList(modifiedData)
        Log.d("SearchFragment", "updateRecyclerView se llamó con ${modifiedData.size} elementos")
    }


}