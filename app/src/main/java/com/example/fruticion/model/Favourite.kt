package com.example.fruticion.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.fruticion.model.Fruit
import com.example.fruticion.model.User
import java.io.Serializable

@Entity(
    primaryKeys = ["favId", "userId"],
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
    @NonNull
    var favId: Long,
    @NonNull
    var userId: Long,
    var fruitId: Long? = null
) : Serializable
