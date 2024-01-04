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
import com.example.fruticion.util.DailyIntakeBuffer
import kotlinx.coroutines.launch

class DailyIntakeViewModel(private val repository: Repository, private val dailyIntakeFruitsList: DailyIntakeBuffer) : ViewModel() {

    private val _fruits = MutableLiveData<List<Fruit>>()
    val fruits: LiveData<List<Fruit>> = _fruits

    private val _nutritions = MutableLiveData<Nutrition>()
    val nutritions: LiveData<Nutrition> = _nutritions

    fun update() {
        viewModelScope.launch {

            if(dailyIntakeFruitsList.dailyIntakeFruitsListIsNotEmpty()){
                Log.i("dailyIntake","Obteniendo frutas del buffer de frutas diarias")
                _fruits.value = dailyIntakeFruitsList.returnDailyIntakeFruitsList()
                _nutritions.value = returnTotalDailyNutrition()
            }else{
                Log.i("dailyIntake","Obteniendo frutas diarias de la BD")
                val fruitList = repository.getAllDailyFruitsByUser()
                _fruits.value=fruitList

                dailyIntakeFruitsList.insertDailyFruitsList(fruitList) //carga toda la lista de frutas y la nutrition al principio
                _nutritions.value = returnTotalDailyNutrition() //Se carga el LiveData con los datos de la nutrition
            }

        }
    }

    private fun returnTotalDailyNutrition() : Nutrition{
        return dailyIntakeFruitsList.returnTotalDailyNutrition()
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
                return DailyIntakeViewModel(
                    (application as FruticionApplication).appContainer.repository,
                    (application as FruticionApplication).appContainer.dailyIntakeBuffer,
                ) as T
            }
        }
    }
}