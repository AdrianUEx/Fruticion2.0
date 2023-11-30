package com.example.fruticion.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.fruticion.activity.LoginActivity
import com.example.fruticion.api.APIError
import com.example.fruticion.api.FruitMapper
import com.example.fruticion.api.FruticionAPI
import com.example.fruticion.model.Favourite
import com.example.fruticion.model.Fruit
import com.example.fruticion.model.User
import com.example.fruticion.model.WeeklyIntake
import java.time.LocalDate


// Repository.k
class Repository private constructor (private val api: FruticionAPI, private val db: FruticionDatabase) {

    /*
    val favFruitsInList = LoginActivity.currentUserId?.let {
        db.favouriteDao().getAllFavFruitsByUser(
            it
        )
    }*/




    //Este metodo es para SearchFragment. Obtiene las frutas de la API, las mapea, las inserta en Room y las devuelve para meterlas en el Adapter del RecyclerView.
    suspend fun getFruits(): List<Fruit> {
        try {
            //llamada a la API
            val serializedFruits = api.getAllFruits()
            //se mapean las frutas de la API
            val readyFruitList = FruitMapper.mapFromSerializedFruitList(serializedFruits)
            //se añaden las frutas a la BD
            db.fruitDao().addFruitList(readyFruitList)

            //se devuelven todas las frutas de la BD
            return db.fruitDao().getAll()

        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }


    // FavouriteFragment
     fun getAllFavFruits(): LiveData<List<Fruit>> {
        return db.favouriteDao().getAllLDFavFruitsByUser(LoginActivity.currentUserId!!)
    }

    suspend fun addFavFruit(fruitId: Long) {
        db.favouriteDao().addFavFruit(Favourite(LoginActivity.currentUserId!!, fruitId))
    }

    suspend fun deleteFavFruit(fruitId: Long) {
        db.favouriteDao().deleteFavById(LoginActivity.currentUserId!!, fruitId)
    }

    //Register activity y Login Activity
    suspend fun insertUser(user: User) {
        db.userDao().insertUser(user)
    }

    suspend fun checkUserByUsername(username: String): Boolean {
        return db.userDao().getUserByUsername(username) == null
    }

    suspend fun getUserByUsername(username: String): User {
        return db.userDao().getUserByUsername(username)
    }

    suspend fun deleteDailyFruits(fechaSistema: LocalDate) {
        db.dailyIntakeDao().deleteDailyfruits(LoginActivity.currentUserId!!, fechaSistema)
    }

    suspend fun deleteWeeklyFruits() {
        db.weeklyIntakeDao().deleteWeeklyfruits(LoginActivity.currentUserId!!)
    }

    suspend fun getOneWeeklyFruit(): WeeklyIntake {
        return db.weeklyIntakeDao().getOneWeeklyFruit(LoginActivity.currentUserId!!)
    }

    //DetailFragment
    suspend fun getFruitById(fruitId: Long): Fruit {
        return db.fruitDao().getFruitById(fruitId)
    }

    suspend fun checkFruitIsFav(fruitId: Long): Boolean {
        return db.favouriteDao().getFavFruitByUser(LoginActivity.currentUserId!!, fruitId) == null
    }

    //Instancia del patron Repository (no tocar)
    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(api: FruticionAPI, db: FruticionDatabase): Repository {
            return instance ?: synchronized(this) {
                instance ?: Repository(api, db).also { instance = it }
            }
        }
    }
}

