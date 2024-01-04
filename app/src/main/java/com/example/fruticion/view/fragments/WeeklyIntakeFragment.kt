package com.example.fruticion.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fruticion.api.Nutrition
import com.example.fruticion.databinding.FragmentWeeklyIntakeBinding
import com.example.fruticion.view.adapters.WeeklyIntakeAdapter
import com.example.fruticion.model.Fruit
import com.example.fruticion.view.viewModel.WeeklyIntakeViewModel


class WeeklyIntakeFragment : Fragment() {

    private var _binding: FragmentWeeklyIntakeBinding? = null
    private val binding get() = _binding!!

    private lateinit var weeklyIntakeAdapter: WeeklyIntakeAdapter
    private var onWeeklyFruitsLoadedListener: OnWeeklyFruitsLoadedListener? = null

    private val weeklyIntakeViewModel: WeeklyIntakeViewModel by viewModels { WeeklyIntakeViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeeklyIntakeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weeklyIntakeViewModel.update()

        val dbFruit = listOf<Fruit>()
        setUpRecyclerView(dbFruit)

        weeklyIntakeViewModel.fruits.observe(viewLifecycleOwner) { weeklyFruitsInList ->
            Log.i("Valor lista frutas semanal", "$weeklyFruitsInList")
            onWeeklyFruitsLoadedListener?.onWeeklyFruitsLoaded(weeklyFruitsInList)

            //obtainWeeklyNutritions(weeklyFruitsInList)

            updateRecyclerView(weeklyFruitsInList)
        }

        weeklyIntakeViewModel.nutritions.observe(viewLifecycleOwner){ weeklyNutrition ->
            //llamada a actualización de las nutritions
            obtainWeeklyNutritionsFromLD(weeklyNutrition)
        }

    }

    private fun obtainWeeklyNutritions(dbFruit: List<Fruit>) {
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

    private fun obtainWeeklyNutritionsFromLD(weeklyNutrition: Nutrition) {
        //Se formatean en String antes de pasarselo a los TextView para evitar el fallo de los 15 ceros
        val formatCalories = String.format("%.2f", weeklyNutrition.calories)
        val formatCarbo = String.format("%.2f", weeklyNutrition.carbohydrates)
        val formatFats = String.format("%.2f", weeklyNutrition.fat)
        val formatSugars = String.format("%.2f", weeklyNutrition.sugar)
        val formatProteins = String.format("%.2f", weeklyNutrition.protein)

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
        val recyclerView = binding.rvFruitWeeklyList
        recyclerView.layoutManager = LinearLayoutManager(context)

        weeklyIntakeAdapter =
            WeeklyIntakeAdapter(dbFruit) { fruitList -> onItemSelected(fruitList.roomId!!) }

        recyclerView.adapter = weeklyIntakeAdapter
    }

    private fun onItemSelected(fruitId: Long) {
        //insertar codigo del recordatorio si se quiere poner aqui dicha funcion
    }

    interface OnWeeklyFruitsLoadedListener {
        fun onWeeklyFruitsLoaded(fruit: List<Fruit>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnWeeklyFruitsLoadedListener) {
            onWeeklyFruitsLoadedListener = context
        }
    }

    private fun updateRecyclerView(newData: List<Fruit>) {
        val modifiedData = ArrayList(newData)
        weeklyIntakeAdapter.updateList(modifiedData)
    }
}