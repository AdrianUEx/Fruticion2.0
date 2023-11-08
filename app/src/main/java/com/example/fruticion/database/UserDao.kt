package com.example.fruticion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

import com.example.fruticion.model.User

@Dao
interface UserDao {
        //Devuelve el usuario al completo con todos sus datos
        @Query("SELECT * FROM user WHERE username LIKE :first LIMIT 1")//Esta query tiene LIMIT para asegurar que es eficiente si o si aunque la consulta este equivocada
        suspend fun findUserByName(first: String): User

        //Devuelve el id del usuario si la insercion ocurrió correctamente
        @Insert
        suspend fun insertUser(user: User): Long

        //Borra un unico usuario si el usuario pasado por parametros existe exactamente en la BD
        @Delete
        suspend fun deleteUser(user: User)

        //Actualiza el usuario de la BD. Room se encarga de encontrar el usuario y actualizar sus campos. Devuelve el id del usuario actualizado si tuvo exito.
        @Update
        suspend fun updateUser(user: User)
}
