package com.example.fruticion.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruticion.database.Repository
import com.example.fruticion.model.Fruit
import com.example.fruticion.model.User
import kotlinx.coroutines.launch

class DetailViewModel () : ViewModel() {

    val detailFruit = MutableLiveData<Fruit>()
    var isFavorite : Boolean = false

    fun getDetailFruit(): LiveData<Fruit> {
        return detailFruit
    }

    fun update(fruit : Fruit){
         detailFruit.postValue(fruit)
    }
}