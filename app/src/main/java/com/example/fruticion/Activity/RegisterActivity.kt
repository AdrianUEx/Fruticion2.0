package com.example.fruticion.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fruticion.R
import com.example.fruticion.databinding.ActivityLoginBinding
import com.example.fruticion.databinding.ActivityRegisterBinding
import com.example.fruticion.util.CredentialCheck

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
                val check = CredentialCheck.join(editTextRegisterUsername.text.toString(), editTextRegisterPassword.text.toString(), editTextConfirmPassword.text.toString())
                if(check.fail)
                    Toast.makeText(binding.root.context, check.msg, Toast.LENGTH_SHORT).show()
                else {
                    Toast.makeText(binding.root.context, check.msg, Toast.LENGTH_SHORT).show()
                    finish()
                }

            }

        }
    }
}