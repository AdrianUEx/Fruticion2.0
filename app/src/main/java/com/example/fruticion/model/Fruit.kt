package com.example.fruticion.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fruticion.api.Nutrition
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//Esta clase debera de actuar como modelo de datos interno de la aplicacion, es decir, una especie de traduccion (mapeado) de la clase receptora de la peticion de la API
@Entity
data class Fruit (
    /*generame los atributos tipicos de una fruta* y que sea serializable*/
    @PrimaryKey(autoGenerate = false)
    var roomId: Long? = null,
    //var apiId: Long? = null,
    var name: String? = null,
    var family: String? = null,
    var order: String? = null,
    var genus: String? = null,
    //var nutritions: Nutrition? = null
) : Serializable



