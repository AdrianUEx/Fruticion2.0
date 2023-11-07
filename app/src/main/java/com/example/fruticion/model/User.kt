package com.example.fruticion.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//@Entity indica una tabla usando los atributos de la clase como columnas de la tabla. Metiendo mas anotaciones se puede personalizar mas la tabla
@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    var userId: Long?, //Con esta linea se declara que el atributo userId es la clave primaria, y ademas se autogenera por Room.

    val username: String = "",
    val password: String = ""
): Serializable