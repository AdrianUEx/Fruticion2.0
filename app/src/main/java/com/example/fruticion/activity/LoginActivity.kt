package com.example.fruticion.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.databinding.ActivityLoginBinding
import com.example.fruticion.util.CredentialCheck
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var db: FruticionDatabase
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializacion de la base de datos
        db = FruticionDatabase.getInstance(applicationContext)!!

        setUpListeners()

        //read settings
        readSettings()
    }

    private fun readSettings(){
        val preferences = PreferenceManager.getDefaultSharedPreferences(this).all

        val rememberme = preferences["preferences_autologin"] as Boolean? ?: false
        val username = preferences["preferences_username"] as String? ?: ""
        val password = preferences["preferences_password"] as String? ?: ""

        if(rememberme) {
            binding.editTextUsername.setText(username)
            binding.editTextPassword.setText(password)
        }
    }

    private fun setUpListeners() {
        with(binding) {

            buttonLogin.setOnClickListener {
                //comprobar credenciales
                //checkLogin()
                navigateToHomeActivity()
            }

            buttonRegister.setOnClickListener {
                navigateToRegister()
            }

        }
    }

    private fun checkLogin(){
        val check = CredentialCheck.login(binding.editTextUsername.text.toString(), binding.editTextPassword.text.toString())
        if(!check.fail){
            lifecycleScope.launch {
                val user = db?.userDao()?.findUserByName(binding.editTextUsername.text.toString())
                if(user != null){
                    val check = CredentialCheck.passwordOk(binding.editTextPassword.text.toString(), user.password)
                    if(check.fail)
                        Toast.makeText(binding.root.context, check.msg, Toast.LENGTH_SHORT).show()
                    else
                        navigateToHomeActivity()
                } else
                    Toast.makeText(binding.root.context, "Invalid username", Toast.LENGTH_SHORT).show()
            }
        } else
            Toast.makeText(binding.root.context, check.msg, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHomeActivity() {
        HomeActivity.start(this)
    }

    private fun navigateToRegister() {
        RegisterActivity.start(this)
    }
}