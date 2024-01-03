package com.example.fruticion.util

import android.content.Context
import com.example.fruticion.api.getNetworkService
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.database.Repository


//Esta clase es para meter aqui las dependencias que se utilizan a lo largo del proyecto en cualquier parte.
// Se entiende por dependencias el acceso a la API, a Room y a Repository. Por tanto, las dependencias son "dependencias de datos" que se dan entre distintas partes de la aplicacion si pintasemos la aplicacion como un esquema.
//Esta clase AppContainer solo se va a crear una vez, por tanto solo va a crear una vez todas las instancias de las dependencias que tiene declaradas. Por eso no se necesita el patrón Singleton en los demas sitios despues de implementar esto.
//Esta clase termina siendo inicializada una unica vez de forma automatica por el propio framework de Android gracias a TiviCloneApplication, ya que hereda de Application() y está declarada en el AndroidManifest.xml
class AppContainer(context: Context?) { //el contexto es necesario para poder crear la instancia de la base de datos.
    //Las dependencias aqui declaradas han de seguir el orden correcto, teniendo que estar declaradas arriba las dependencias que se usen para inicializar otras.
    private val networkService = getNetworkService()
    private val db = FruticionDatabase.getInstance(context!!)
    val repository = Repository(networkService, db!!) //Esta es la unica instancia publica porque es la unica instancia que se va a utilizar en el resto del proyecto

    val fruitDetailMap = FruitDetailDataMapper() //DataMapper de frutas para DetailFragment

    val fruitImagesMap = FruitImagesMap() //Mapa normal con las imágenes de las frutas solo para DetailFragment (en principio)

    val dailyIntakeBuffer = DailyIntakeBuffer() //Buffer para guardar la lista de frutas diaria del usuario (más adelante guardará tambien la nutrición)
}