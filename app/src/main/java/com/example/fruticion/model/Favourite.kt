package com.example.fruticion.model

import androidx.room.Entity
import androidx.room.ForeignKey
import java.io.Serializable

@Entity(
    primaryKeys = ["userId", "fruitId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"]
        ),
        ForeignKey(
            entity = Fruit::class,
            parentColumns = ["roomId"],
            childColumns = ["fruitId"]
        )
    ]
)
data class Favourite(
    var userId: Long,
    var fruitId: Long
) : Serializable
