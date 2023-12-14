package com.example.fruticion.view.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.fruticion.FruticionApplication
import com.example.fruticion.R
import com.example.fruticion.database.Repository
import com.example.fruticion.model.Fruit
import com.example.fruticion.model.User
import kotlinx.coroutines.launch

class DetailViewModel (private val repository: Repository) : ViewModel() {

    val detailFruit = MutableLiveData<Fruit>()
    var isFavorite : Boolean = false

    fun getDetailFruit(): LiveData<Fruit> {
        return detailFruit
    }

    fun update(fruitID : Long){
        viewModelScope.launch {
            isFavorite = !repository.checkFruitIsFav(fruitID)
            val dbFruit = repository.getFruitById(fruitID)
            detailFruit.postValue(dbFruit)
            Log.i("valor inicial corazon","$isFavorite")
        }

    }

    fun onFavoriteButtonClick(fruitId: Long){
        viewModelScope.launch {
            if (repository.checkFruitIsFav(fruitId)) {
                repository.addFavFruit(fruitId)

            } else {
                repository.deleteFavFruit(fruitId)
            }
        }
    }

    fun onAddDailyButtonClick(fruitId: Long){
        viewModelScope.launch {
            repository.insertDailyFruit(fruitId)

            repository.insertWeeklyFruit(fruitId)
        }
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
                return DetailViewModel(
                    (application as FruticionApplication).appContainer.repository,
                ) as T
            }
        }
    }
}