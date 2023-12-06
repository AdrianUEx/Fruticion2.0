package com.example.fruticion.view.viewModel

import android.text.Spannable.Factory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.fruticion.FruticionApplication
import com.example.fruticion.database.Repository
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