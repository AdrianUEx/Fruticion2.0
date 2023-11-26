package com.example.fruticion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fruticion.model.Fruit
import com.example.fruticion.model.WeeklyIntake

@Dao
interface WeeklyIntakeDao {
    //Le entra la fruta semanal ya cargada
    @Insert
    suspend fun insertWeeklyFruit(weeklyIntake: WeeklyIntake)

    //coge un WeeklyIntake cualquiera
    @Query("SELECT * FROM weeklyintake WHERE additionDate = (SELECT max(additionDate) FROM weeklyintake WHERE userId=:userId LIMIT 1) AND userId = :userId LIMIT 1")
    suspend fun getOneWeeklyFruit(userId: Long) : WeeklyIntake

    @Query("SELECT * FROM weeklyintake INNER JOIN Fruit ON weeklyintake.fruitId = Fruit.roomId WHERE weeklyintake.userId=:userId")
    suspend fun getAllWeeklyFruitsByUser(userId: Long) : List<Fruit>


    @Query("DELETE FROM weeklyintake WHERE userId = :userId")
    suspend fun deleteWeeklyfruits(userId: Long)
}