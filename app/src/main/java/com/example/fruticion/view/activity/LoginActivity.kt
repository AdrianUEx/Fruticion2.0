package com.example.fruticion.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.example.fruticion.FruticionApplication
import com.example.fruticion.database.Repository
import com.example.fruticion.databinding.ActivityLoginBinding
import com.example.fruticion.util.CredentialCheck
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var repository: Repository

    companion object {
        //Esta variable se puede modificar para que no salte error al usarla en los metodos del Dao quitandole el ? y metiendole un valor cualquiera
        var currentUserId: Long? =
            null //no se pone public porque es redundante. Es var y no val porque su valor va a ser modificado de null a otra cosa.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val appContainer = (this.application as FruticionApplication).appContainer //Esta linea funciona porque estamos en una Activity
        repository = appContainer.repository

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    //Navega hasta HomeActivity y borra las frutas diarias y semanales del usuario.
    private fun navigateToHomeActivity() {
        deleteDailyandWeeklyFruits()

        HomeActivity.start(this)
    }

    //Invocado por navigateToHomeActivity(). Borra las frutas diarias y semanales del usuario.
    private fun deleteDailyandWeeklyFruits() {
        val fechaSistema = LocalDate.now()
        Log.i("fechaActual", "$fechaSistema")

        lifecycleScope.launch {
            //obtiene el ultimo valor introducido en la tabla de fruta diaria
            val ultimaFrutaDiaria = repository.getOneDailyFruit()

            if (ultimaFrutaDiaria != null) {
                if (ultimaFrutaDiaria.additionDate < fechaSistema) {
                    //borra la tabla de frutas diarias del usuario (no toda la tabla). Falla al borrar la fruta al cambiar de año.
                    repository.deleteDailyFruits()
                }
            }


            //obtiene el ultimo valor introducido en la tabla de fruta semanal
            val frutaEjemplo = repository.getOneWeeklyFruit()

            if (frutaEjemplo != null) {
                //se obtiene la semana de la fecha de frutaEjemplo
                val semFrutaEjemplo =
                    frutaEjemplo.additionDate.get(WeekFields.of(Locale.getDefault()).weekOfYear())

                // Obtiene el número de semana del año actual utilizando WeekFields
                val numSemActual =
                    fechaSistema.get(WeekFields.of(Locale.getDefault()).weekOfYear())

                //Si el numero de la semana actual es mayor que el mayor numero de la semana de la tabla del usuario, se borra.
                //Tambien, si la semana de comienzo de año es 0 porque empieza a mitad de semana, no se borra (dale vueltas)
                //Con la condicion después del OR se comprueba el caso del cambio de año, ya que si el año empieza en Lunes,
                //la semana del nuevo año es 1 y la semana del año anterior es 52, con lo que la condicion previa no sirve
                if (numSemActual > semFrutaEjemplo && numSemActual > 0 || (numSemActual == 1 && semFrutaEjemplo == 52))
                    repository.deleteWeeklyFruits()
            }
        }
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