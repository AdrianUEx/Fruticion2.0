package com.example.fruticion.util

import com.example.fruticion.model.Fruit
import android.util.Log

//Esta clase sirve para guardar las frutas cuando se seleccionan en alguna lista, de manera que no haya que recurrir a la base de datos para recuperar los datos
//Esto carga la RAM más, ya que tiene que conservar la estructura de datos durante la sesión del usuario, pero si el hardware lo soporta, confiere más velocidad ya que no se realizan tantas llamadas a la BD una vez el mapa está cargado.
class FruitDetailDataMapper {

    //En este mapa las claves son la clave primaria de las Frutas y el dato es la propia fruta
    private var fruitMap: MutableMap<Long, Fruit> = mutableMapOf()

    // Método para agregar un elemento al mapa
    fun addElement(clave: Long, valor: Fruit) {
        fruitMap[clave] = valor
        Log.i("FruitDetailMap","Fruta añadida al mapa con id $clave")
    }

    // Método para obtener un elemento del mapa. Puede ser nulo si no se encuentra dicho elemento.
    fun retrieveElement(clave: Long): Fruit? {
        Log.i("FruitDetailMap","Se intenta recuperar la fruta con id $clave")
        return fruitMap[clave]
    }

    // Método para eliminar un elemento del mapa
    fun deleteElement(clave: Long) {
        fruitMap.remove(clave)
        Log.i("FruitDetailMap","Eliminada la fruta con id $clave")
    }

    // Comprueba si existe el elemento con la clave pasada por parametros
    fun containsElement(clave: Long): Boolean{
        Log.i("FruitDetailMap","Comprobando si existe la fruta con clave $clave en el mapa")
        return fruitMap.contains(clave)
    }

    // Borra el mapa entero. Necesario al cerrar sesión para controlar mejor la gestión de memoria, aunque si vuelve a iniciar sesion el mismo usuario tiene que volver a cargarse el mapa
    fun clearMap(){
        Log.i("FruitDetailMap","Borrando todas las frutas del mapa")
        fruitMap.clear()
        Log.i("FruitDetailMap","Mapa borrado en su totalidad")
    }
}