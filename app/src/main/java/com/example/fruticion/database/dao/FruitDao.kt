package com.example.fruticion.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.example.fruticion.model.Fruit

@Dao
interface FruitDao {

    @Query("SELECT * FROM Fruit")
    suspend fun getAll(): List<Fruit>

    //Esta consulta recupera los datos justos necesarios para las listas de frutas. Al recuperar menos datos, es más rapido y eficiente
    @Query("SELECT roomId, name, family, `order` FROM Fruit")
    suspend fun getAllFruitsForList():List<Fruit>

    @Query("SELECT * FROM Fruit WHERE name =  :fruitName ")
    suspend fun getFruitByName(fruitName: String): Fruit

    @Query("SELECT * FROM Fruit WHERE roomId =  :id ")
    suspend fun getFruitById(id: Long): Fruit

    @Insert(onConflict = IGNORE)
    suspend fun addFruit(fruit: Fruit)

    @Insert(onConflict = IGNORE)
    suspend fun addFruitList(fruitList: List<Fruit>)
}