package com.example.fruticion.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fruticion.R
import com.example.fruticion.databinding.ActivityLoginBinding
import com.example.fruticion.util.CredentialCheck

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {

            buttonLogin.setOnClickListener {
                //comprobar credenciales
                val check = CredentialCheck.login(editTextUsername.text.toString(), editTextPassword.text.toString())
                if(check.fail)
                    Toast.makeText(binding.root.context, check.msg, Toast.LENGTH_SHORT).show()
                else {
                    Toast.makeText(binding.root.context, check.msg, Toast.LENGTH_SHORT).show()
                    navigateToHomeActivity()
                }
            }

            buttonRegister.setOnClickListener {
                navigateToRegister()
            }

        }
    }

    private fun navigateToHomeActivity() {
        HomeActivity.start(this)
    }

    private fun navigateToRegister() {
        RegisterActivity.start(this)
    }
}