package com.example.fruticion.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.fruticion.api.APIError
import com.example.fruticion.api.FruitMapper
import com.example.fruticion.api.FruticionAPI
import com.example.fruticion.model.DailyIntake
import com.example.fruticion.model.Favourite
import com.example.fruticion.model.Fruit
import com.example.fruticion.model.User
import com.example.fruticion.model.WeeklyIntake
import com.example.fruticion.view.activity.LoginActivity
import java.time.LocalDate
import java.time.LocalTime

// Repository.k
class Repository(private val api: FruticionAPI, private val db: FruticionDatabase) {

    private var lastUpdateTimeMillis: Long =
        0L //Esta variable contendrá el tiempo en milisegundos de la ultima actualizacion de datos desde la API

    val favFruitsInList = LoginActivity.currentUserId?.let { userId ->
        Log.i("Valor id usuario antes de obtener los favs", "$userId")
        db.favouriteDao().getAllLDFavFruitsByUser(userId)
    }

    val dailyFruitsInList = LoginActivity.currentUserId?.let { userId ->
        Log.i("Valor id usuario antes de obtener los favs", "$userId")
        db.dailyIntakeDao().getAllLDDailyFruitsByUser(userId)
    }

    val weeklyFruitsInList = LoginActivity.currentUserId?.let { userId ->
        Log.i("Valor id usuario antes de obtener los favs", "$userId")
        db.weeklyIntakeDao().getAllLDWeeklyFruitsByUser(userId)
    }

    // SearchFragment

    //Comprueba si se puede realizar una peticion a la API desde la ultima vez que se hizo. La primera vez que se enciende la aplicación siempre entra.
    fun shouldFetchFruits(): Boolean {
        //Se comprueba si el tiempo desde el ultimo fetch es mayor que el tiempo especificado para volver a hacer el fetch (una hora)
        val timeFromLastFetch = System.currentTimeMillis() - lastUpdateTimeMillis
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS
    }

    //Este metodo es para SearchFragment. Obtiene las frutas de la API, las mapea, las inserta en Room y las devuelve para meterlas en el Adapter del RecyclerView.
    suspend fun getFruits(): List<Fruit> {
        try {
            if (shouldFetchFruits()) {
                //llamada a la API
                val serializedFruits = api.getAllFruits()
                Log.i("Llamada API", "Llamada realizada a la API")
                //Evita que se inserten frutas vacías en la BD y asi la aplicación funcione normalmente
                if (serializedFruits.isNotEmpty()) {
                    //se actualiza la fecha del ultimo fetch realizado
                    lastUpdateTimeMillis = System.currentTimeMillis()
                    //se mapean las frutas de la API
                    val readyFruitList = FruitMapper.mapFromSerializedFruitList(serializedFruits)
                    //se insertan las frutas en la BD
                    db.fruitDao().addFruitList(readyFruitList)
                }
            }

            //se devuelven todas las frutas de la BD
            return db.fruitDao().getAllFruitsForList()

        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }


    // FavouriteFragment
    fun getAllFavFruits(): LiveData<List<Fruit>> {
        return db.favouriteDao().getAllLDFavFruitsByUser(LoginActivity.currentUserId!!)
    }

    suspend fun getAllFavFruitsByUser(): List<Fruit> {
        return db.favouriteDao().getAllFavFruitsByUser(LoginActivity.currentUserId!!)
    }

    suspend fun getAllFavFruitsByUserForList(): List<Fruit> {
        return db.favouriteDao().getAllFavFruitsByUserForList(LoginActivity.currentUserId!!)
    }

    suspend fun getFavFruitByUser(fruitId: Long): Fruit {
        return db.favouriteDao().getFavFruitByUser(LoginActivity.currentUserId!!, fruitId)
    }

    suspend fun addFavFruit(fruitId: Long) {
        db.favouriteDao().addFavFruit(Favourite(LoginActivity.currentUserId!!, fruitId))
    }

    suspend fun deleteFavFruit(fruitId: Long) {
        db.favouriteDao().deleteFavById(LoginActivity.currentUserId!!, fruitId)
    }

    //Register Activity y Login Activity
    suspend fun insertUser(user: User) {
        db.userDao().insertUser(user)
    }

    suspend fun updateUser(newName: String, newPassword: String) {
        db.userDao().updateUser(
            User(
                LoginActivity.currentUserId,
                newName,
                newPassword
            )
        )
    }

    suspend fun checkUserByUsername(username: String): Boolean {
        return db.userDao().getUserByUsername(username) == null
    }

    suspend fun getUserByUsername(username: String): User {
        return db.userDao().getUserByUsername(username)
    }

    suspend fun getUserById(): User {
        return db.userDao().getUserById(LoginActivity.currentUserId)
    }

    suspend fun deleteUserById() {
        db.userDao().deleteUserById(LoginActivity.currentUserId!!)
    }

    //DailyIntakeFragment
    suspend fun insertDailyFruit(fruitId: Long) {
        db.dailyIntakeDao().insertDailyFruit(
            DailyIntake(
                fruitId,
                LoginActivity.currentUserId!!,
                LocalDate.now(),
                LocalTime.now()
            )
        )
    }

    suspend fun deleteDailyFruits(fechaSistema: LocalDate) {
        db.dailyIntakeDao().deleteDailyfruits(LoginActivity.currentUserId!!, fechaSistema)
    }

    suspend fun getAllDailyFruitsByUser(): List<Fruit> {
        return db.dailyIntakeDao().getAllDailyFruitsByUser(LoginActivity.currentUserId!!)
    }

    suspend fun getAllDailyFruitsByUserForList(): List<Fruit> {
        return db.dailyIntakeDao().getAllDailyFruitsByUserForList(LoginActivity.currentUserId!!)
    }

    //WeeklyIntakeFragment
    suspend fun insertWeeklyFruit(fruitId: Long) {
        db.weeklyIntakeDao().insertWeeklyFruit(
            WeeklyIntake(
                fruitId,
                LoginActivity.currentUserId!!,
                LocalDate.now(),
                LocalTime.now()
            )
        )
    }

    suspend fun deleteWeeklyFruits() {
        db.weeklyIntakeDao().deleteWeeklyfruits(LoginActivity.currentUserId!!)
    }

    suspend fun getOneWeeklyFruit(): WeeklyIntake {
        return db.weeklyIntakeDao().getOneWeeklyFruit(LoginActivity.currentUserId!!)
    }

    suspend fun getAllWeeklyFruitsByUser(): List<Fruit> {
        return db.weeklyIntakeDao().getAllWeeklyFruitsByUser(LoginActivity.currentUserId!!)
    }

    suspend fun getAllWeeklyFruitsByUserForList(): List<Fruit> {
        return db.weeklyIntakeDao().getAllWeeklyFruitsByUserForList(LoginActivity.currentUserId!!)
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

        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long =
            3600000 //Este tiempo es una hora en milisegundos, ya que es muy poco probable que en nuestra API haya cambios frecuentes.

        @Volatile
        private var instance: Repository? = null

        fun getInstance(api: FruticionAPI, db: FruticionDatabase): Repository {
            return instance ?: synchronized(this) {
                instance ?: Repository(api, db).also { instance = it }
            }
        }
    }
}