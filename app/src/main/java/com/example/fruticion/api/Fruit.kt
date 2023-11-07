package com.example.fruticion.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//Esta clase es la clase que recibirá el resultado de la peticion. No es el modelo de datos interno para la aplicacion.
data class Fruit(
    @SerializedName("name") var name: String? = null,
    @SerializedName("family") var family: String? = null,
    @SerializedName("order")var order: String? = null,
    @SerializedName("genus")var genus: String? = null,
    @SerializedName("nutritions")var nutritions: Nutrition? = null
    //Segun la documentacion de la API, nutritions es un JSON, pero como tambien dice que los valores son int cuando tienen coma, pues no me fio
): Serializable