package com.example.fruticion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fruticion.model.DailyIntake
import com.example.fruticion.model.Fruit
import java.time.LocalDate

@Dao
interface DailyIntakeDao {

    //Le entra la fruta diaria ya cargada
    @Insert
    suspend fun insertDailyFruit(dailyIntake: DailyIntake)

    @Query("SELECT * FROM dailyintake INNER JOIN Fruit ON dailyintake.fruitId = Fruit.roomId WHERE dailyintake.userId=:userId")
    suspend fun getAllDailyFruitsByUser(userId: Long) : List<Fruit>


    @Query("DELETE FROM dailyintake WHERE userId = :userId AND additionDate < :deleteDate ")
    suspend fun deleteDailyfruits(userId: Long, deleteDate: LocalDate)
}