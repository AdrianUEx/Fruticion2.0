package com.example.fruticion.util

import android.util.Log
import com.example.fruticion.api.Nutrition
import com.example.fruticion.model.Fruit

class WeeklyIntakeBuffer {
    private var weeklyIntakeFruitsList: MutableList<Fruit> = mutableListOf() //Inicializa la lista a una lista vacia (no null)
    private var totalWeeklyNutrition : Nutrition = Nutrition(0.0,0.0,0.0,0.0,0.0)


    fun weeklyIntakeFruitsListIsNotEmpty() : Boolean{
        return weeklyIntakeFruitsList.isNotEmpty()
    }

    // Inserta la nueva fruta a la lista e invoca a la actualizacion de los datos nutricionales
    fun insertWeeklyFruit(fruit: Fruit){
        weeklyIntakeFruitsList.add(fruit)
        updateTotalNutrition(fruit)
    }

    //Este metodo solo se invoca una vez al principio, pero tecnicamente podría usarse mas veces si cambiase la aplicacion
    fun insertWeeklyFruitsList(fruitList : List<Fruit>){
        weeklyIntakeFruitsList.addAll(fruitList) //Inserta la lista al final de la lista (si la lista está vacia, el final es también es el principio)
        calcTotalNutritionFromList() //carga totalDailyNutrition con los datos nutricionales de la lista de frutas
    }

    private fun calcTotalNutritionFromList(){
        /* var totalCalories = 0.0
         var totalSugars = 0.0
         var totalFats = 0.0
         var totalCarbo = 0.0
         var totalProtein = 0.0*/

        for (it in weeklyIntakeFruitsList) {
            totalWeeklyNutrition.calories = totalWeeklyNutrition.calories?.plus(it.calories!!)
            totalWeeklyNutrition.sugar = totalWeeklyNutrition.sugar?.plus(it.sugar!!)
            totalWeeklyNutrition.fat = totalWeeklyNutrition.fat?.plus(it.fat!!)
            totalWeeklyNutrition.carbohydrates = totalWeeklyNutrition.carbohydrates?.plus(it.carbohydrates!!)
            totalWeeklyNutrition.protein = totalWeeklyNutrition.protein?.plus(it.protein!!)
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
        Log.i("weeklyIntake","Actualizando totalWeeklyNutrition con la nueva fruta")

        totalWeeklyNutrition.calories = totalWeeklyNutrition.calories?.plus(fruit.calories!!)
        totalWeeklyNutrition.sugar = totalWeeklyNutrition.sugar?.plus(fruit.sugar!!)
        totalWeeklyNutrition.fat = totalWeeklyNutrition.fat?.plus(fruit.fat!!)
        totalWeeklyNutrition.carbohydrates = totalWeeklyNutrition.carbohydrates?.plus(fruit.carbohydrates!!)
        totalWeeklyNutrition.protein = totalWeeklyNutrition.protein?.plus(fruit.protein!!)

        Log.i("weeklyIntake","Calorías semanales totales: ${totalWeeklyNutrition.calories}")
        Log.i("weeklyIntake","Azucar semanal totales: ${totalWeeklyNutrition.sugar}")
        Log.i("weeklyIntake","Grasas semanales totales: ${totalWeeklyNutrition.fat}")
        Log.i("weeklyIntake","Carbo semanales totales: ${totalWeeklyNutrition.carbohydrates}")
        Log.i("weeklyIntake","Proteinas semanales totales: ${totalWeeklyNutrition.protein}")
    }

    fun returnTotalWeeklyNutrition() : Nutrition {
        return totalWeeklyNutrition
    }

    fun clearWeeklyIntakeFruitsList(){
        weeklyIntakeFruitsList.clear()
    }

    fun returnWeeklyIntakeFruitsList() : List<Fruit>{
        return weeklyIntakeFruitsList //No se si aqui habria que añadir el .toList()
    }
}