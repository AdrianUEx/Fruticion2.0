package com.example.fruticion.Model

import java.io.Serializable

data class Fruit (
    /*generame los atributos tipicos de una fruta* y que sea serealizable*/
    var name: String = "",
    var description: String = "",
    var color: String = "",
    var price: String = "",
    var origin: String = "",
    var size: String = ""
) : Serializable