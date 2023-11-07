package com.example.fruticion.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val service: FruticionAPI by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.fruityvice.com/api/fruit/")
        .client(okHttpClient)//Este cliente no lo ha explicado. Valdrá para algo.
        .addConverterFactory(GsonConverterFactory.create())//El convertidor de Gson para serializar y deserializar
        .build()

    retrofit.create(FruticionAPI::class.java)//esta linea crea el objeto de Retrofit usando interfaz TVShowAPI de mas abajo (no confundir con TvShow). Esto hace que se pueda invocar sus metodos para concatenar los endpoints de la URL base y los parametros de dichas URL.
}

//Este metodo es para obtener la variable privada "service" declarada mas arriba, ya que es privada
fun getNetworkService() = service

//En esta interfaz es donde se hacen las llamadas a la API concatenando los endpoint
interface FruticionAPI{
    @GET("all")
    fun getAllFruits(): Call<Fruit>//Call<> es un wrapper de Retrofit. Mientras que Fruit es el tipo de objeto que se va a obtener de la invocacion de getAllFruits(), Call lo va a envolver para poder realizar la llamada de forma sincrona o asincrona (bloqueante o no bloqueante)

    //Tengo duda aqui, porque la URL no es id=6 por ejemplo, sino que es directamente 6
    @GET("id")
    fun getFruitsById(): Call<Fruit>

    //Tengo duda aqui, porque la URL no es name=apple por ejemplo, sino que es directamente apple
    @GET("name")
    fun getFruitsByName(): Call<Fruit>

    @GET("family/:")
    fun getFruitsByFamily(
        @Query("family") family: String
    ): Call<Fruit>

    @GET("genus/:")
    fun getFruitsByGenus(
        @Query("genus") genus: String
    ): Call<Fruit>

    @GET("order/:")
    fun getFruitsByOrder(
        @Query("order") order: String
    ): Call<Fruit>

    @GET(":nutrition")
    fun getFruitsByMinNutrition(
        @Query("min") min: Double
    ):Call<Fruit>

    @GET(":nutrition")
    fun getFruitsByMaxNutrition(
        @Query("max") max: Double
    ):Call<Fruit>

    @GET(":nutrition")
    fun getFruitsByRangeNutrition(
        @Query("min") min: Double,
        @Query("max") max: Double
    ):Call<Fruit>
}
//Esta pequeña clase solo es para mostrar errores de la API usando la libreria Throwable
class APIError(message: String, cause: Throwable?) : Throwable(message, cause)

//Esta interfaz es solo para poder realizar las llamadas callback siempre de la misma manera
interface APICallback {
    fun onCompleted(fruits:List<Fruit?>)
    fun onError(cause: Throwable)
}