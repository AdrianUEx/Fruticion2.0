package com.example.fruticion.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fruticion.model.Fruit
import com.example.fruticion.model.Favourite
@Dao
interface FavouriteDao {


    @Query("SELECT * FROM Favourite INNER JOIN Fruit ON Favourite.fruitId = Fruit.roomId WHERE Favourite.userId=:userId")
    suspend fun getAllFavFruitsByUser(userId: Long): List<Fruit>
    @Query("SELECT * FROM Favourite INNER JOIN Fruit ON Favourite.fruitId = Fruit.roomId WHERE Favourite.userId=:userId")
    fun getAllLDFavFruitsByUser(userId: Long): LiveData<List<Fruit>>

    @Query("SELECT * FROM Favourite INNER JOIN Fruit ON Favourite.fruitId = Fruit.roomId WHERE Favourite.userId=:userId AND Favourite.fruitId=:fruitId")
    suspend fun getFavFruitByUser(userId: Long, fruitId: Long): Fruit

    @Insert
    suspend fun addFavFruit(favourite: Favourite)

    @Query("DELETE FROM Favourite WHERE userId = :userId AND fruitId = :fruitId")
    suspend fun deleteFavById(userId: Long, fruitId: Long)
}