package com.example.fruticion.api

import com.example.fruticion.model.Fruit

object FruitMapper {
    fun mapFromSerializedFruit(serializedFruit: SerializedFruit): Fruit {
        val nutrition = serializedFruit.nutritions
        return Fruit(
            name = serializedFruit.name,
            family = serializedFruit.family,
            order = serializedFruit.order,
            genus = serializedFruit.genus,

            calories = nutrition?.calories,
            carbohydrates = nutrition?.carbohydrates,
            sugar =  nutrition?.sugar,
            fat = nutrition?.fat,
            protein = nutrition?.protein
        )
    }
}