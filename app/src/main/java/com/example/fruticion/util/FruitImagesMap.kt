package com.example.fruticion.util

import com.example.fruticion.R

class FruitImagesMap {
    private var map: MutableMap<String, Int> = mutableMapOf()

    //constructor primario por defecto
    init {
        map["Apple"] = R.drawable.apple
        map["Apricot"] = R.drawable.apricot
        map["Avocado"] = R.drawable.avocado
        map["Banana"] = R.drawable.banana
        map["Blackberry"] = R.drawable.blackberry
        map["Blueberry"] = R.drawable.blueberry
        map["Cherry"] = R.drawable.cherry
        map["Cranberry"] = R.drawable.cranberry
        map["Dragonfruit"] = R.drawable.dragonfruit
        map["Durian"] = R.drawable.durian
        map["Feijoa"] = R.drawable.feijoa
        map["Fig"] = R.drawable.fig
        map["Gooseberry"] = R.drawable.gooseberry
        map["Grape"] = R.drawable.grape
        map["GreenApple"] = R.drawable.greenapple
        map["Guava"] = R.drawable.guava
        map["Hazelnut"] = R.drawable.hazelnut
        map["Horned Melon"] = R.drawable.horned_melon
        map["Jackfruit"] = R.drawable.jackfruit
        map["Kiwi"] = R.drawable.kiwi
        map["Kiwifruit"] = R.drawable.kiwifruit
        map["Lemon"] = R.drawable.lemon
        map["Lime"] = R.drawable.lime
        map["Lingonberry"] = R.drawable.lingonberry
        map["Lychee"] = R.drawable.lychee
        map["Mango"] = R.drawable.mango
        map["Mangosteen"] = R.drawable.mangosteen
        map["Melon"] = R.drawable.melon
        map["Morus"] = R.drawable.morus
        map["Orange"] = R.drawable.orange
        map["Papaya"] = R.drawable.papaya
        map["Passionfruit"] = R.drawable.passionfruit
        map["Peach"] = R.drawable.peach
        map["Pear"] = R.drawable.pear
        map["Persimmon"] = R.drawable.persimmon
        map["Pineapple"] = R.drawable.pineapple
        map["Pitahaya"] = R.drawable.pitahaya
        map["Plum"] = R.drawable.plum
        map["Pomegranate"] = R.drawable.pomegranate
        map["Pomelo"] = R.drawable.pomelo
        map["Raspberry"] = R.drawable.raspberry
        map["Strawberry"] = R.drawable.strawberry
        map["Tangerine"] = R.drawable.tangerine
        map["Tomato"] = R.drawable.tomato
        map["Watermelon"] = R.drawable.watermelon

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