package com.example.fruticion.database

import androidx.room.Dao
import androidx.room.Query
import com.example.fruticion.model.Fruit

@Dao
interface FruitDao {

    @Query("SELECT * FROM fruit")
    suspend fun getAll() : List<Fruit>

    @Query("SELECT * FROM fruit WHERE name =  :fruitName ")
    suspend fun getFruitByName(fruitName : String): Fruit

    @Query("SELECT * FROM fruit WHERE roomId =  :id ")
    suspend fun getFruitById(id : Long): Fruit


}