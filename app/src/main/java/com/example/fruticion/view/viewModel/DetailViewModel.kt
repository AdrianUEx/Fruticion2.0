package com.example.fruticion.view.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.fruticion.FruticionApplication
import com.example.fruticion.database.Repository
import com.example.fruticion.model.Fruit
import com.example.fruticion.util.DailyIntakeBuffer
import com.example.fruticion.util.FruitDetailDataMapper
import com.example.fruticion.util.WeeklyIntakeBuffer
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: Repository,
    private val fruitDetailMap: FruitDetailDataMapper,
    private val dailyIntakeBuffer: DailyIntakeBuffer,
    private val weeklyIntakeBuffer: WeeklyIntakeBuffer
) : ViewModel() {

    private val _detailFruit = MutableLiveData<Fruit>()
    val detailFruit: LiveData<Fruit> = _detailFruit
    var isFavorite: Boolean = false

    fun update(fruitID: Long) {
        viewModelScope.launch {
            //Establece en el atributo si la fruta está en la la lista de favoritos del usuario en la BD
            isFavorite = !repository.checkFruitIsFav(fruitID)


            val tiempoIni= System.currentTimeMillis()
            val dbFruit: Fruit?
            if (fruitDetailMap.containsElement(fruitID)) {
                //obtiene la fruta del mapa, ya que existe en él
                dbFruit = fruitDetailMap.retrieveElement(fruitID)
            } else {
                //obtiene la fruta como tal del repositorio, ya que no existe en el mapa
                dbFruit = repository.getFruitById(fruitID)
                if(dbFruit!=null){
                    //inserta la fruta que no existia en el mapa, para que exista hasta que se cierre sesión
                    fruitDetailMap.addElement(fruitID,dbFruit)
                }
            }
            val tiempoFinal=System.currentTimeMillis() - tiempoIni
            Log.i("FruitDetailMap","Tiempo total rec fruta: $tiempoFinal")
            _detailFruit.postValue(dbFruit!!)
        }
    }

    fun onFavoriteButtonClick(fruitId: Long) {
        viewModelScope.launch {
            if (repository.checkFruitIsFav(fruitId)) {
                repository.addFavFruit(fruitId)
            } else {
                repository.deleteFavFruit(fruitId)
            }
        }
    }

    fun onAddDailyButtonClick(fruitId: Long) {
        viewModelScope.launch {
            repository.insertDailyAndWeeklyFruit(fruitId) //hace dos llamadas a BD. Con esto se activa el .observe() en DailyIntake y WeeklyIntake
            //repository.insertWeeklyFruit(fruitId)

            val fruit = repository.getFruitById(fruitId)
            dailyIntakeBuffer.insertDailyFruit(fruit) //TODO: revisar este metodo para posiblemente meterlo dentro de insertDailyAndWeeklyFruit()
            weeklyIntakeBuffer.insertWeeklyFruit(fruit)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return DetailViewModel(
                    (application as FruticionApplication).appContainer.repository,
                    (application as FruticionApplication).appContainer.fruitDetailMap,
                    (application as FruticionApplication).appContainer.dailyIntakeBuffer,
                    (application as FruticionApplication).appContainer.weeklyIntakeBuffer,
                ) as T
            }
        }
    }
}
