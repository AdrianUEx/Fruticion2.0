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
import com.example.fruticion.api.Nutrition
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

        dailyIntakeViewModel.update() //se carga/actualiza la lista de frutas

        val dbFruit = listOf<Fruit>()
        setUpRecyclerView(dbFruit) // se inicializa el RecyclerView aunque sea con una lista vacia

        dailyIntakeViewModel.fruits.observe(viewLifecycleOwner) { dailyFruitsInList ->
            Log.i("Valor lista frutas diaria", "$dailyFruitsInList")

           // obtainDailyNutritions(dailyFruitsInList) //se calcula la info nutricional y se muestra por pantalla

            //obtainDailyNutritions2(dailyIntakeViewModel.returnTotalDailyNutrition()) //esta llamada aqui tarda demasiado en cargar los datos nutricionales por pantalla

            onDailyFruitsLoadedListener?.onDailyFruitsLoaded(dailyFruitsInList)

            updateRecyclerView(dailyFruitsInList) //se actualiza la RecyclerView, tenga elementos o no
        }

        dailyIntakeViewModel.nutritions.observe(viewLifecycleOwner) { dailyNutrition ->
            //llamada a actualización de las nutritions
            obtainDailyNutritionsFromLD(dailyNutrition)
        }
    }

    private fun obtainDailyNutritions(fruitList: List<Fruit>) {
        var totalCalories = 0.0
        var totalSugars = 0.0
        var totalFats = 0.0
        var totalCarbo = 0.0
        var totalProtein = 0.0

        for (it in fruitList) {
            totalCalories += it.calories!!
            totalSugars += it.sugar!!
            totalFats += it.fat!!
            totalCarbo += it.carbohydrates!!
            totalProtein += it.protein!!
        }


        //Se formatean en String antes de pasarselo a los TextView para evitar el fallo de los 15 ceros
        /* val formatCalories = String.format("%.2f", dailyNutrition.calories)
         val formatCarbo = String.format("%.2f", dailyNutrition.carbohydrates)
         val formatFats = String.format("%.2f", dailyNutrition.fat)
         val formatSugars = String.format("%.2f", dailyNutrition.sugar)
         val formatProteins = String.format("%.2f", dailyNutrition.protein)*/

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

    private fun obtainDailyNutritionsFromLD(dailyNutrition: Nutrition) {
        //Se formatean en String antes de pasarselo a los TextView para evitar el fallo de los 15 ceros
        val formatCalories = String.format("%.2f", dailyNutrition.calories)
        val formatCarbo = String.format("%.2f", dailyNutrition.carbohydrates)
        val formatFats = String.format("%.2f", dailyNutrition.fat)
        val formatSugars = String.format("%.2f", dailyNutrition.sugar)
        val formatProteins = String.format("%.2f", dailyNutrition.protein)

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