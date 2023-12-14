package com.example.fruticion.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.fruticion.FruticionApplication
import com.example.fruticion.database.Repository
import com.example.fruticion.model.Fruit
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: Repository) : ViewModel() {

    val fruits = MutableLiveData<List<Fruit>>()

    fun getListFruit(): LiveData<List<Fruit>> {
        return fruits
    }

    fun update() {
        viewModelScope.launch {
            val fruitsList = repository.getFruits()
            fruits.postValue(fruitsList)
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
                return SearchViewModel(
                    (application as FruticionApplication).appContainer.repository,
                ) as T
            }
        }
    }
}