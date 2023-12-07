package com.example.fruticion.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fruticion.model.User

class ProfileViewModel : ViewModel(){

    val user = MutableLiveData<User>()

    fun getUser(): LiveData<User> {
        return user
    }

    fun update(newUser: User){
        user.postValue(newUser)
    }
}