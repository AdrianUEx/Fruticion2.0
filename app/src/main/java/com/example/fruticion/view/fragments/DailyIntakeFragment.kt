package com.example.fruticion.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruticion.databinding.FragmentDailyIntakeBinding
import com.example.fruticion.model.Fruit
import com.example.fruticion.view.adapters.DailyIntakeAdapter
import com.example.fruticion.view.viewModel.DailyIntakeViewModel


class DailyIntakeFragment : Fragment() {
    private var _binding: FragmentDailyIntakeBinding? = null
    private val binding get() = _binding!!

    private lateinit var dailyIntakeAdapter: DailyIntakeAdapter
    private var onDailyFruitsLoadedListener: OnDailyFruitsLoadedListener? = null

    private val dailyIntakeViewModel: DailyIntakeViewModel by viewModels { DailyIntakeViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDailyIntakeBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dailyIntakeViewModel.update()

        val dbFruit = listOf<Fruit>()
        setUpRecyclerView(dbFruit)

        dailyIntakeViewModel.fruits.observe(viewLifecycleOwner) { dailyFruitsInList ->
            Log.i("Valor lista frutas diaria", "$dailyFruitsInList")
            onDailyFruitsLoadedListener?.onDailyFruitsLoaded(dailyFruitsInList)

            obtainDailyNutritions(dailyFruitsInList)
            updateRecyclerView(dailyFruitsInList)
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

        with(binding) {
            valueTotalCalories.text = formatCalories
            valueTotalCarbo.text = formatCarbo
            valueTotalFats.text = formatFats
            valueTotalSugars.text = formatSugars
            valueTotalProteins.text = formatProteins
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //METODOS RECYCLER VIEW-----------------------------------------------------------------------------------------------------------------------------------
    private fun setUpRecyclerView(dbFruit: List<Fruit>) {
        val recyclerView = binding.rvFruitDailyList
        recyclerView.layoutManager = LinearLayoutManager(context)

        dailyIntakeAdapter =
            DailyIntakeAdapter(dbFruit) { fruitList -> onItemSelected(fruitList.roomId!!) }

        recyclerView.adapter = dailyIntakeAdapter
    }

    fun updateRecyclerView(newData: List<Fruit>) {
        val modifiedData = ArrayList(newData)
        dailyIntakeAdapter.updateList(modifiedData)
    }

    private fun onItemSelected(fruitId: Long) {
        // insertar codigo del recordatorio si se quiere poner aqui dicha funcion
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


}