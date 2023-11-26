package com.example.fruticion.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

//Esta clase debera de actuar como modelo de datos interno de la aplicacion, es decir, una especie de traduccion (mapeado) de la clase receptora de la peticion de la API
@Entity(indices = [Index(value = ["name"], unique = true)])
data class Fruit (

    @PrimaryKey(autoGenerate = false)//Esto creo que hay que ponerlo a true
    var roomId: Long?,
    //var apiId: Long? = null,
    var name: String? = null,
    var family: String? = null,
    var order: String? = null,
    var genus: String? = null,

    var calories: Double? = null,
    var fat: Double? = null,
    var sugar: Double? = null,
    var carbohydrates: Double? = null,
    var protein: Double? = null
    //var nutritions: Nutrition? = null
) : Serializable



