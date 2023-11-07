package com.example.fruticion.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fruticion.model.User

//Esta clase actua como instancia de la base de datos. Solo debe haber una unica instancia de la base de datos, por lo que usa el patron Singleton.
@Database(entities = [User::class], version = 1) abstract class FruticionDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
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
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}