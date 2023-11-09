package com.example.fruticion.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fruticion.R
import com.example.fruticion.databinding.ActivityEditProfileBinding
import com.example.fruticion.databinding.ActivityHomeBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

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

        setUpListeners()

    }

    private fun setUpListeners() {
        with(binding){

            buttonSaveChanges.setOnClickListener{
                finish()
            }

            buttonCancelChanges?.setOnClickListener{
                finish()
            }
        }
    }


}