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
            parentColumns = ["fruitId"],
            childColumns = ["fruitId"]
        )
    ]
)
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    var favId: Long? = null,
    var userId: Long? = null,
    var fruitId: Long? = null
) : Serializable
