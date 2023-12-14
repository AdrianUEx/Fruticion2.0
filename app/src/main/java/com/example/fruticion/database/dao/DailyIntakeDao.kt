package com.example.fruticion.database.dao

import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM dailyintake INNER JOIN Fruit ON dailyintake.fruitId = Fruit.roomId WHERE dailyintake.userId=:userId")
    fun getAllLDDailyFruitsByUser(userId: Long) : LiveData<List<Fruit>>

    //Esta consulta recupera estrictamente los datos necesarios para la lista. Al recuperar menos datos, es mas eficiente.
    @Query("SELECT roomId, name, family, `order` FROM dailyintake INNER JOIN Fruit ON dailyintake.fruitId = Fruit.roomId WHERE dailyintake.userId=:userId")
    suspend fun getAllDailyFruitsByUserForList(userId: Long) : List<Fruit>

    @Query("DELETE FROM dailyintake WHERE userId = :userId AND additionDate < :deleteDate ")
    suspend fun deleteDailyfruits(userId: Long, deleteDate: LocalDate)
}