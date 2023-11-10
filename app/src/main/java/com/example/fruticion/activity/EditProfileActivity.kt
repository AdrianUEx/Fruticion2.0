package com.example.fruticion.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.fruticion.activity.LoginActivity.Companion.currentUserId
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.databinding.ActivityEditProfileBinding
import com.example.fruticion.model.User
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var db: FruticionDatabase
    companion object {
        fun start(
            context: Context,
        ) {
            val intent = Intent(context, EditProfileActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se obtiene la instancia de la base de datos. La sintaxis siempre debe ser asi.
        db = FruticionDatabase.getInstance(applicationContext)!!

        setUpListeners()

    }

    private fun setUpListeners() {
        with(binding){

            buttonSaveChanges.setOnClickListener{

                //Hay que usar toString() porque text (getText() por detras) devuelve de tipo CharSequence!
                var newName = binding.newUsernameEditText?.text.toString()
                var newPassword = binding.newPasswordEditText?.text.toString()

                //corrutina obligatoria para hacer operaciones con la BD o con la API (tareas pesadas)
                lifecycleScope.launch {
                    db.userDao().updateUser(User(
                        currentUserId, //este es un companion object que viene cargado desde el login
                        newName,
                        newPassword
                    ))
                }

                finish()
            }

            buttonCancelChanges?.setOnClickListener{
                finish()
            }
        }
    }


}