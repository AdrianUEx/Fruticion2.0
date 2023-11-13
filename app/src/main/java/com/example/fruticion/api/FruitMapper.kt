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
}