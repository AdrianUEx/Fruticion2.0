package com.example.fruticion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.example.fruticion.model.User

@Dao
interface UserDao {
        //Devuelve el usuario al completo con todos sus datos recuperandolo por su nombre de usuario
        @Query("SELECT * FROM user WHERE username LIKE :first LIMIT 1")//Esta query tiene LIMIT para asegurar que es eficiente si o si aunque la consulta este equivocada
        suspend fun findUserByName(first: String): User

        //Devuelve el usuario al completo con todos sus datos recuperandolo por su id
        @Query("SELECT * FROM user WHERE userId LIKE :userId LIMIT 1")
        suspend fun getUserById(userId: Long?): User

        @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1")
        suspend fun getUserByUsername(username: String): User

        //Devuelve el id del usuario si la insercion ocurrió correctamente
        @Insert
        suspend fun insertUser(user: User): Long

        //Borra un unico usuario buscándolo por su id (ahorra una llamada a la BD)
        @Query("DELETE FROM user WHERE userId = :userId")
        suspend fun deleteUserById(userId: Long)

        //Actualiza el usuario de la BD. Room se encarga de encontrar el usuario y actualizar sus campos usando la clave primario. Puede devolver un int con el numero de filas actualizadas
        @Update
        suspend fun updateUser(user: User)
}
