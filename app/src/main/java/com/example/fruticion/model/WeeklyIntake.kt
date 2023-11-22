package com.example.fruticion.model

import androidx.room.Entity
import androidx.room.ForeignKey
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    primaryKeys = ["userId", "fruitId", "additionTime"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Fruit::class,
            parentColumns = ["roomId"],
            childColumns = ["fruitId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class WeeklyIntake(
    var fruitId: Long,
    var userId: Long,
    var additionDate: LocalDate,
    var additionTime: LocalTime
) : Serializable
