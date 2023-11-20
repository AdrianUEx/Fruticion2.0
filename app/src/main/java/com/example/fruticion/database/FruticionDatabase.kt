package com.example.fruticion.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fruticion.api.LocalDateConverter
import com.example.fruticion.model.DailyIntake
import com.example.fruticion.model.Favourite
import com.example.fruticion.model.Fruit
import com.example.fruticion.model.User
import com.example.fruticion.model.WeeklyIntake

//Esta clase actua como instancia de la base de datos. Solo debe haber una unica instancia de la base de datos, por lo que usa el patron Singleton.
@Database(entities = [User::class, Fruit::class, Favourite::class, DailyIntake::class, WeeklyIntake::class], version = 2)
@TypeConverters(LocalDateConverter::class)
abstract class FruticionDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun fruitDao(): FruitDao
    abstract fun favouriteDao(): FavouriteDao
    abstract fun dailyIntakeDao(): DailyIntakeDao
    abstract fun weeklyIntakeDao(): WeeklyIntakeDao

    // Esto esta dentro de un companion object (actua como static) para que sea invocable desde cualquier lugar de la aplicacion
    companion object {
        private var INSTANCE: FruticionDatabase? = null
        fun getInstance(context: Context): FruticionDatabase? {
            if (INSTANCE == null) {
                synchronized(FruticionDatabase::class) {
                INSTANCE = Room.databaseBuilder( context, FruticionDatabase::class.java, "fruticion.db" ).build()
                }
            }
            return INSTANCE
        }
        //TODO: invocar a destroyInstance() al pulsar el boton CerrarSesion (no se necesita la instancia de la BD si se ha cerrado sesión, ahorrando memoria)
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}