package com.example.fruticion.util

import com.example.fruticion.model.Fruit

class DailyIntakeBuffer {
    private var dailyIntakeFruitsList: MutableList<Fruit> = mutableListOf() //Inicializa la lista a una lista vacia (no null)

    fun dailyIntakeFruitsListIsNotEmpty() : Boolean{
        return dailyIntakeFruitsList.isNotEmpty()
    }

    fun insertDailyFruit(fruit:Fruit){
        dailyIntakeFruitsList.add(fruit)
    }

    fun insertDailyFruitsList(fruitList : List<Fruit>){
        dailyIntakeFruitsList.addAll(fruitList) //Inserta la lista al final de la lista (si la lista está vacia, el final es también es el principio)
    }
    fun clearDailyIntakeFruitsList(){
        dailyIntakeFruitsList.clear()
    }

    fun returnDailyIntakeFruitsList() : List<Fruit>{
        return dailyIntakeFruitsList //No se si aqui habria que añadir el .toList()
    }
}