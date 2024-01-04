package com.example.fruticion.util

import android.util.Log
import com.example.fruticion.api.Nutrition
import com.example.fruticion.model.Fruit

class DailyIntakeBuffer {
    private var dailyIntakeFruitsList: MutableList<Fruit> = mutableListOf() //Inicializa la lista a una lista vacia (no null)
    private var totalDailyNutrition : Nutrition = Nutrition(0.0,0.0,0.0,0.0,0.0)


    fun dailyIntakeFruitsListIsNotEmpty() : Boolean{
        return dailyIntakeFruitsList.isNotEmpty()
    }

    // Inserta la nueva fruta a la lista e invoca a la actualizacion de los datos nutricionales
    fun insertDailyFruit(fruit:Fruit){
        dailyIntakeFruitsList.add(fruit)
        updateTotalNutrition(fruit)
    }

    //Este metodo solo se invoca una vez al principio, pero tecnicamente podría usarse mas veces si cambiase la aplicacion
    fun insertDailyFruitsList(fruitList : List<Fruit>){
        dailyIntakeFruitsList.addAll(fruitList) //Inserta la lista al final de la lista (si la lista está vacia, el final es también es el principio)
        calcTotalNutritionFromList() //carga totalDailyNutrition con los datos nutricionales de la lista de frutas
    }

    private fun calcTotalNutritionFromList(){
       /* var totalCalories = 0.0
        var totalSugars = 0.0
        var totalFats = 0.0
        var totalCarbo = 0.0
        var totalProtein = 0.0*/

        for (it in dailyIntakeFruitsList) {
            totalDailyNutrition.calories = totalDailyNutrition.calories?.plus(it.calories!!)
            totalDailyNutrition.sugar = totalDailyNutrition.sugar?.plus(it.sugar!!)
            totalDailyNutrition.fat = totalDailyNutrition.fat?.plus(it.fat!!)
            totalDailyNutrition.carbohydrates = totalDailyNutrition.carbohydrates?.plus(it.carbohydrates!!)
            totalDailyNutrition.protein = totalDailyNutrition.protein?.plus(it.protein!!)
        }
        /*
        totalDailyNutrition.calories=totalCalories
        totalDailyNutrition.sugar=totalSugars
        totalDailyNutrition.fat= totalFats
        totalDailyNutrition.carbohydrates= totalCarbo
        totalDailyNutrition.protein= totalProtein*/
    }

    //Este metodo se invoca cada vez que se añade una fruta a la ingesta
    private fun updateTotalNutrition(fruit: Fruit){
        Log.i("dailyIntake","Actualizando totalDailyNutrition con la nueva fruta")

        totalDailyNutrition.calories = totalDailyNutrition.calories?.plus(fruit.calories!!)
        totalDailyNutrition.sugar = totalDailyNutrition.sugar?.plus(fruit.sugar!!)
        totalDailyNutrition.fat = totalDailyNutrition.fat?.plus(fruit.fat!!)
        totalDailyNutrition.carbohydrates = totalDailyNutrition.carbohydrates?.plus(fruit.carbohydrates!!)
        totalDailyNutrition.protein = totalDailyNutrition.protein?.plus(fruit.protein!!)

        Log.i("dailyIntake","Calorías diarias totales: ${totalDailyNutrition.calories}")
        Log.i("dailyIntake","Azucar diario totales: ${totalDailyNutrition.sugar}")
        Log.i("dailyIntake","Grasas diarias totales: ${totalDailyNutrition.fat}")
        Log.i("dailyIntake","Carbo diarios totales: ${totalDailyNutrition.carbohydrates}")
        Log.i("dailyIntake","Proteinas diarias totales: ${totalDailyNutrition.protein}")
    }

    fun returnTotalDailyNutrition() : Nutrition {
        return totalDailyNutrition
    }

    fun clearDailyIntakeFruitsList(){
        dailyIntakeFruitsList.clear()
    }

    fun returnDailyIntakeFruitsList() : List<Fruit>{
        return dailyIntakeFruitsList //No se si aqui habria que añadir el .toList()
    }
}