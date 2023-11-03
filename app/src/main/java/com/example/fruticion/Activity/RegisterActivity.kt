package com.example.fruticion.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fruticion.R
import com.example.fruticion.databinding.ActivityLoginBinding
import com.example.fruticion.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

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
         binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {

            buttonRegister.setOnClickListener {
                //comprobar credenciales
                finish()
            }

        }
    }
}