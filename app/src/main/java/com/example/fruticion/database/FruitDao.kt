package com.example.fruticion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fruticion.model.Fruit

@Dao
interface FruitDao {

    @Query("SELECT * FROM Fruit")
    suspend fun getAll() : List<Fruit>

    //Este metodo es como el getAll() pero solo coge los atributos que hacen falta para las CardView y para el resto del codigo. No reduce el tiempo de carga del RecyclerView.
    @Query("SELECT roomId, name, family,`order` FROM Fruit")
    suspend fun getAllFruitsReduced() : List<Fruit>

    @Query("SELECT * FROM Fruit WHERE name =  :fruitName ")
    suspend fun getFruitByName(fruitName : String): Fruit

    @Query("SELECT * FROM Fruit WHERE roomId =  :id ")
    suspend fun getFruitById(id : Long): Fruit
    @Insert
    suspend fun addFruit(fruit: Fruit)
}