package com.example.fruticion.view.viewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.fruticion.FruticionApplication
import com.example.fruticion.api.Nutrition
import com.example.fruticion.database.Repository
import com.example.fruticion.model.Fruit
import com.example.fruticion.util.WeeklyIntakeBuffer
import kotlinx.coroutines.launch

class WeeklyIntakeViewModel(
    private val repository: Repository,
    private val weeklyIntakeBuffer: WeeklyIntakeBuffer
) : ViewModel() {

    private val _fruits = MutableLiveData<List<Fruit>>()
    val fruits: LiveData<List<Fruit>> = _fruits

    private val _nutritions = MutableLiveData<Nutrition>()
    val nutritions: LiveData<Nutrition> = _nutritions

    fun update() {
        viewModelScope.launch {
            if (weeklyIntakeBuffer.weeklyIntakeFruitsListIsNotEmpty()) {
                Log.i("weeklyIntake","Obteniendo frutas del buffer de frutas semanales")
                _fruits.value = weeklyIntakeBuffer.returnWeeklyIntakeFruitsList()
                _nutritions.value = returnTotalWeeklyNutrition()
            } else {
                Log.i("weeklyIntake", "Obteniendo frutas semanales de la BD")
                val fruitList = repository.getAllWeeklyFruitsByUser()
                _fruits.value = fruitList

                weeklyIntakeBuffer.insertWeeklyFruitsList(fruitList) //carga toda la lista de frutas y la nutrition al principio
                _nutritions.value = returnTotalWeeklyNutrition() //Se carga el LiveData con los datos de la nutrition
            }

        }
    }

    private fun returnTotalWeeklyNutrition(): Nutrition {
        return weeklyIntakeBuffer.returnTotalWeeklyNutrition()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T { // Get the Application object from extras

                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return WeeklyIntakeViewModel(
                    (application as FruticionApplication).appContainer.repository,
                    (application as FruticionApplication).appContainer.weeklyIntakeBuffer,
                ) as T
            }
        }
    }
}