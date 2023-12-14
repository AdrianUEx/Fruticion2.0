package com.example.fruticion.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.fruticion.FruticionApplication
import com.example.fruticion.api.getNetworkService
import com.example.fruticion.database.FruticionDatabase
import com.example.fruticion.database.Repository
import com.example.fruticion.databinding.ActivityRegisterBinding
import com.example.fruticion.model.User
import com.example.fruticion.util.CredentialCheck
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    //private lateinit var db: FruticionDatabase
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var repository: Repository
    companion object {
        fun start(
            context: Context,
        ) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (this.application as FruticionApplication).appContainer
        repository = appContainer.repository
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializacion de la base de datos
        /*db = FruticionDatabase.getInstance(applicationContext)!!

        repository = Repository.getInstance(getNetworkService(), db)*/

setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {

            buttonRegister.setOnClickListener {
                lifecycleScope.launch {
                    if (repository.checkUserByUsername(editTextRegisterUsername.text.toString())) {
                        val check = CredentialCheck.join(
                            editTextRegisterUsername.text.toString(),
                            editTextRegisterPassword.text.toString(),
                            editTextConfirmPassword.text.toString()
                        )

                        if (check.fail)
                            Toast.makeText(binding.root.context, check.msg, Toast.LENGTH_SHORT)
                                .show()
                        else {

                            val user = User(
                                null,
                                editTextRegisterUsername.text.toString(),
                                editTextRegisterPassword.text.toString()
                            )

                           repository.insertUser(user)

                            Toast.makeText(binding.root.context, check.msg, Toast.LENGTH_SHORT)
                                .show()
                            finish()

                        }

                    }
                    else
                        Toast.makeText(binding.root.context, "Nombre de ususario ocupado", Toast.LENGTH_SHORT).show()
                }

            }

            buttonCancelRegister.setOnClickListener {
                finish()
            }

            buttonBackToLogin.setOnClickListener {
                finish()
            }

        }
    }
}