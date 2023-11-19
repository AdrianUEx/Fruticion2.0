package com.example.fruticion.api

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter {
    @TypeConverter
    fun LocalDatefromString(date: String): LocalDate{
        return LocalDate.parse(date); //Devuelve un LocalDate a partir de un String
    }

    @TypeConverter
    fun LocalDateToString(date:LocalDate):String{
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return date.format(formatter)//Devuelve el String de la fecha que entra por parametros con el formato del formateador
    }
}