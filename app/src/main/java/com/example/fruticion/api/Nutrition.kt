package com.example.fruticion.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//Esta clase representa la información nutricional de una fruta. Como tambien se cargara con los datos de la API, deberá usar @SerializedName y esas cosas
data class Nutrition (
    @SerializedName("calories") var calories: Double? = null,
    @SerializedName("fat") var fat: Double? = null,
    @SerializedName("sugar") var sugar: Double? = null,
    @SerializedName("carbohydrates") var carbohydrates: Double? = null,
    @SerializedName("protein") var protein: Double? = null

) : Serializable