package com.example.fruticion.api

import com.example.fruticion.model.Fruit

object FruitMapper {
    fun mapFromSerializedFruit(serializedFruit: SerializedFruit): Fruit {
        val nutrition = serializedFruit.nutritions
        return Fruit(
            roomId = null,
            name = serializedFruit.name,
            family = serializedFruit.family,
            order = serializedFruit.order,
            genus = serializedFruit.genus,

            calories = nutrition?.calories,
            fat = nutrition?.fat,
            sugar =  nutrition?.sugar,
            carbohydrates = nutrition?.carbohydrates,
            protein = nutrition?.protein
        )
    }

    fun mapFromSerializedFruitList(serializedFruitList: List<SerializedFruit>) : MutableList<Fruit>{
        var nutrition : Nutrition?
        val fruitList : MutableList<Fruit> = mutableListOf()

        for(sF in serializedFruitList){
            nutrition = sF.nutritions
            val loadedFruit = Fruit(
                roomId = null,
                name = sF.name,
                family = sF.family,
                order = sF.order,
                genus = sF.genus,

                calories = nutrition?.calories,
                fat = nutrition?.fat,
                sugar =  nutrition?.sugar,
                carbohydrates = nutrition?.carbohydrates,
                protein = nutrition?.protein
            )
            fruitList.add(loadedFruit)
        }
        return fruitList
    }
}