package com.example.fruticion.api.typeconverters

import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeConverter {
    @TypeConverter
    fun stringToLocalTime(date: String) : LocalTime{
        return LocalTime.parse(date)

    }

    //En el formato se llega hasta los milisegundos por si el usuario machaca el boton
    @TypeConverter
    fun localTimeToString(date: LocalTime) : String{
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
        return date.format(formatter)
    }
}