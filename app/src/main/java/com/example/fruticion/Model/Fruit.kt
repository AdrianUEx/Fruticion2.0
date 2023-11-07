package com.example.fruticion.Model

import java.io.Serializable

//Esta clase debera de actuar como modelo de datos interno de la aplicacion, es decir, una especie de traduccion (mapeado) de la clase receptora de la peticion de la API
data class Fruit (
    /*generame los atributos tipicos de una fruta* y que sea serealizable*/
    var name: String = "",
    var description: String = "",
    var color: String = "",
    var price: String = "",
    var origin: String = "",
    var size: String = ""
) : Serializable



