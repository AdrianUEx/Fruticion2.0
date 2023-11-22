package com.example.fruticion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.fruticion.model.Fruit

@Dao
interface FruitDao {

    @Query("SELECT * FROM Fruit")
    suspend fun getAll(): List<Fruit>

    @Query("SELECT * FROM Fruit WHERE name =  :fruitName ")
    suspend fun getFruitByName(fruitName: String): Fruit

    @Query("SELECT * FROM Fruit WHERE roomId =  :id ")
    suspend fun getFruitById(id: Long): Fruit

    @Insert(onConflict = IGNORE)
    suspend fun addFruit(fruit: Fruit)
}