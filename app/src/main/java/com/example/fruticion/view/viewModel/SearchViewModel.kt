package com.example.fruticion.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fruticion.model.Fruit

class SearchViewModel : ViewModel() {

    val fruits = MutableLiveData<List<Fruit>>()

    fun getListFruit(): LiveData<List<Fruit>> {
        return fruits
    }

    fun update(fruitsList : List<Fruit>){
        fruits.postValue(fruitsList)
    }
}