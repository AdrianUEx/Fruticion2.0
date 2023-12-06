package com.example.fruticion.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.example.fruticion.FruticionApplication
import com.example.fruticion.api.getNetworkService
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.database.Repository
import com.example.fruticion.databinding.ActivityLoginBinding
import com.example.fruticion.util.CredentialCheck
import com.example.fruticion.util.FruitImagesMap
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class LoginActivity : AppCompatActivity() {

    //private lateinit var db: FruticionDatabase
    private lateinit var binding: ActivityLoginBinding
    private lateinit var repository: Repository

    companion object {
        //Esta variable se puede modificar para que no salte error al usarla en los metodos del Dao quitandole el ? y metiendole un valor cualquiera
        var currentUserId: Long? =
            null //no se pone public porque es redundante. Es var y no val porque su valor va a ser modificado de null a otra cosa.

        //El mapa de imagenes de frutas para que esté disponible para toda la aplicación.
        lateinit var fruitImagesMap: FruitImagesMap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (this.application as FruticionApplication).appContainer
        repository = appContainer.repository

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializacion de la base de datos
       /* db = FruticionDatabase.getInstance(applicationContext)!!

        repository = Repository.getInstance(getNetworkService(), db)*/

        setUpListeners()

        //read settings
        readSettings()
    }

    private fun setUpListeners() {
        with(binding) {
            buttonLogin.setOnClickListener {
                //comprobar credenciales
                checkLogin()
                //descomentar cuando se quieran hacer pruebas de otros componentes en lugar de estar haciendo el login continuamente
                //navigateToHomeActivity()
            }

            buttonRegister.setOnClickListener {
                navigateToRegister()
            }
        }
    }

    private fun checkLogin() {
        with(binding) {
            val check = CredentialCheck.login(
                editTextUsername.text.toString(),
                editTextPassword.text.toString()
            )
            if (!check.fail) {
                lifecycleScope.launch {
                    val user = repository.getUserByUsername(editTextUsername.text.toString())

                    if (user != null) {
                        //se crea el CredentialCheck para comprobar si la contraseña es correcta, porque en este punto ya se ha comprobado que el usuario ya existe por su nombre
                        val passOkCheck = CredentialCheck.passwordOk(
                            editTextPassword.text.toString(),
                            user.password
                        )
                        if (passOkCheck.fail)
                            Toast.makeText(root.context, passOkCheck.msg, Toast.LENGTH_SHORT)
                                .show()
                        else {
                            currentUserId =
                                user.userId //obtiene el id de Room del usuario actual de la sesión.
                            fruitImagesMap = FruitImagesMap()
                            navigateToHomeActivity()
                        }
                    } else // si el usuario no existe
                        Toast.makeText(root.context, "Invalid username", Toast.LENGTH_SHORT)
                            .show()
                }
            } else
                Toast.makeText(root.context, check.msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToHomeActivity() {

        val fechaSistema = LocalDate.now()
        lifecycleScope.launch {
            //borra la tabla de frutas diarias del usuario (no toda la tabla)
            repository.deleteDailyFruits(fechaSistema)

            // Obtiene el número de semana del año actual utilizando WeekFields
            val numeroSemanaActual =
                fechaSistema.get(WeekFields.of(Locale.getDefault()).weekOfYear())

            //obtiene el ultimo valor introducido en la tabla
            val frutaEjemplo = repository.getOneWeeklyFruit()

            if (frutaEjemplo != null) {
                //se obtiene la semana de la fecha de frutaEjemplo
                val semanaFrutaEjemplo =
                    frutaEjemplo.additionDate.get(WeekFields.of(Locale.getDefault()).weekOfYear())

                //si el numero de la semana actual es mayor que el mayor numero de la semana de la tabla del usuario, se borra.
                //tambien, si la semana de comienzo de año es 0 porque empieza a mitad de semana, no se borra (dale vueltas)
                if (numeroSemanaActual > semanaFrutaEjemplo && numeroSemanaActual > 0)
                    repository.deleteWeeklyFruits()
            }
        }
        HomeActivity.start(this)
    }

    //este metodo es necesario porque si no peta y no coge el contexto
    private fun navigateToRegister() {
        RegisterActivity.start(this)
    }

    private fun readSettings() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this).all

        val rememberme = preferences["preferences_autologin"] as Boolean? ?: false
        val username = preferences["preferences_username"] as String? ?: ""
        val password = preferences["preferences_password"] as String? ?: ""

        if (rememberme) {
            binding.editTextUsername.setText(username)
            binding.editTextPassword.setText(password)
            checkLogin()
        }
    }
}