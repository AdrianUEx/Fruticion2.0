package com.example.fruticion.util

import com.example.fruticion.R

class FruitImagesMap {
    private var map: MutableMap<String, Int> = mutableMapOf()

    //constructor primario por defecto
    init {
        map["Rosales"] = R.drawable.rosales
        map["Zingiberales"] = R.drawable.zingiberales
        map["Solanales"] = R.drawable.solanales
        map["Malvales"] = R.drawable.malvales
        map["Ericales"] = R.drawable.ericales
        map["Sapindales"] = R.drawable.sapindales
        map["Poales"] = R.drawable.poales
        map["Saxifragales"] = R.drawable.saxifragales
        map["Maplpighiales"] = R.drawable.malpighiales
        map["Cucurbitales"] = R.drawable.cucurbitales
        map["Myrtales"] = R.drawable.myrtales
        map["Caricacea"] = R.drawable.caricacea
        map["Cucurbitaceae"] = R.drawable.cucurbitaceae
        map["Caryophyllales"] = R.drawable.caryophyllales
        map["Vitales"] = R.drawable.vitales
        map["Myrtoideae"] = R.drawable.myrtoideae
        map["Laurales"] = R.drawable.laurales
        map["Fagales"] = R.drawable.fagales
        // map["Struthioniformes"] = R.drawable. //Este es el valor del kiwi(animal)

    }


    fun contieneClave(clave: String): Boolean {
        return map.containsKey(clave)
    }

    fun obtenerValor(clave: String): Int? {
        return map[clave]
    }

    fun obtenerClaves(): Set<String> {
        return map.keys
    }

    fun obtenerValores(): Collection<Int> {
        return map.values
    }

}