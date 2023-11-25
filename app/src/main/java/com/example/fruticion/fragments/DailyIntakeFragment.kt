package com.example.fruticion.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruticion.R
import com.example.fruticion.activity.LoginActivity
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.databinding.FragmentDailyIntakeBinding
import com.example.fruticion.databinding.FragmentFavoriteBinding
import com.example.fruticion.model.Fruit
import kotlinx.coroutines.launch


class DailyIntakeFragment : Fragment() {
    private var _binding: FragmentDailyIntakeBinding? = null
    private val binding get() = _binding!!

    private lateinit var dailyIntakeAdapter: DailyIntakeAdapter
    private var onDailyFruitsLoadedListener: OnDailyFruitsLoadedListener? = null

    private lateinit var db: FruticionDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDailyIntakeBinding.inflate(inflater, container, false)

        db = FruticionDatabase.getInstance(requireActivity().applicationContext)!!
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val dbFruit = db.dailyIntakeDao().getAllDailyFruitsByUser(LoginActivity.currentUserId!!)

            onDailyFruitsLoadedListener?.onDailyFruitsLoaded(dbFruit)
            setUpRecyclerView(dbFruit)

            obtainDailyNutritions(dbFruit)
        }

    }

    private fun obtainDailyNutritions(dbFruit: List<Fruit>) {
        var totalCalories = 0.0
        var totalSugars = 0.0
        var totalFats = 0.0
        var totalCarbo = 0.0
        var totalProtein = 0.0

        for (it in dbFruit) {
            totalCalories += it.calories!!
            totalSugars += it.sugar!!
            totalFats += it.fat!!
            totalCarbo += it.carbohydrates!!
            totalProtein += it.protein!!
        }
        //Se formatean en String antes de pasarselo a los TextView para evitar el fallo de los 15 ceros
        val formatCalories = String.format("%.2f", totalCalories)
        val formatCarbo = String.format("%.2f", totalCarbo)
        val formatFats = String.format("%.2f", totalFats)
        val formatSugars = String.format("%.2f", totalSugars)
        val formatProteins = String.format("%.2f", totalProtein)

        with(binding){
            valueTotalCalories.text = formatCalories
            valueTotalCarbo.text = formatCarbo
            valueTotalFats.text = formatFats
            valueTotalSugars.text= formatSugars
            valueTotalProteins.text = formatProteins
        }

    }


    //METODOS RECYCLER VIEW-----------------------------------------------------------------------------------------------------------------------------------
    private fun setUpRecyclerView(dbFruit: List<Fruit>) {
        val recyclerView = binding.rvFruitDailyList
        recyclerView.layoutManager = LinearLayoutManager(context)

        dailyIntakeAdapter =
            DailyIntakeAdapter(dbFruit) { fruitList -> onItemSelected(fruitList.roomId!!) }

        recyclerView.adapter = dailyIntakeAdapter
    }

    private fun onItemSelected(fruitId: Long) {
        //TODO: insertar codigo del recordatorio
    }

    interface OnDailyFruitsLoadedListener {
        fun onDailyFruitsLoaded(fruit: List<Fruit>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDailyFruitsLoadedListener) {
            onDailyFruitsLoadedListener = context
        }
    }

    fun updateRecyclerView(newData: List<Fruit>) {
        val modifiedData = ArrayList(newData)
        dailyIntakeAdapter.updateList(modifiedData)
    }
}