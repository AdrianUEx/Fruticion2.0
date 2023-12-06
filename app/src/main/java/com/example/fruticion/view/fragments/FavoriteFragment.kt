package com.example.fruticion.view.fragments

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
import com.example.fruticion.view.activity.LoginActivity.Companion.currentUserId
import com.example.fruticion.api.getNetworkService
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.database.Repository
import com.example.fruticion.databinding.FragmentFavoriteBinding
import com.example.fruticion.view.adapters.FavoriteAdapter
import com.example.fruticion.model.Fruit
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteAdapter: FavoriteAdapter
    private var onFavFruitsLoadedListener: OnFavFruitsLoadedListener? = null

    private lateinit var db: FruticionDatabase
    private lateinit var repository : Repository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        db = FruticionDatabase.getInstance(requireActivity().applicationContext)!!
        repository = Repository.getInstance(getNetworkService(), db)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("Antes de llegar a lambda","MarcoXupala")
        lifecycleScope.launch {
            val dbFruit = repository.getAllFavFruitslist()
            onFavFruitsLoadedListener?.onFavFruitsLoaded(dbFruit)
            setUpRecyclerView(dbFruit)
        }

        repository.favFruitsInList?.observe(viewLifecycleOwner) { favFruitsInList ->
            Log.i("Valor lista frutas fav", "$favFruitsInList")
            updateRecyclerView(favFruitsInList)
        }

        /*lifecycleScope.launch {
            val dbFruit = db.favouriteDao().getAllFavFruitsByUser(currentUserId!!)
            onFavFruitsLoadedListener?.onFavFruitsLoaded(dbFruit)
            setUpRecyclerView(dbFruit)
        }*/

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//--METODOS RECYCLERVIEW---------------------------------------------------------------------------------------------

    private fun setUpRecyclerView(dbFruit: List<Fruit>) {
        Log.i("Valor lista frutas fav", "$dbFruit")
        val recyclerView = binding.rvFruitFavList
        recyclerView.layoutManager = LinearLayoutManager(context)

        favoriteAdapter = FavoriteAdapter(dbFruit) { fruitList -> onItemSelected(fruitList.roomId!!)}

        recyclerView.adapter = favoriteAdapter
    }

    private fun onItemSelected(fruitId : Long) {
        findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(fruitId=fruitId))
    }

    interface OnFavFruitsLoadedListener {
        fun onFavFruitsLoaded(fruit : List<Fruit>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnFavFruitsLoadedListener){
            onFavFruitsLoadedListener = context
        }
    }

    fun updateRecyclerView(newData: List<Fruit>) {
        val modifiedData = ArrayList(newData)
        favoriteAdapter.updateList(modifiedData)
       }

}