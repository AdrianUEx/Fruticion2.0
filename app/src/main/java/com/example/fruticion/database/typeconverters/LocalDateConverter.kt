package com.example.fruticion.database.typeconverters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateConverter {
    @TypeConverter
    fun stringToLocalDate(date: String): LocalDate{
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDate.parse(date,formatter); //Devuelve un LocalDate a partir de un String
    }

    @TypeConverter
    fun localDateToString(date:LocalDate):String{
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return date.format(formatter)//Devuelve el String de la fecha que entra por parametros con el formato del formateador
    }
}