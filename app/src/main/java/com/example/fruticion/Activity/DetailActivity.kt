package com.example.fruticion.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fruticion.model.Fruit
import com.example.fruticion.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
    }

    fun setUpUI() {
        val fruit = intent.getSerializableExtra("fruit") as Fruit
        binding.textDetailName.text = fruit.name
        binding.valueDetailCalories.text = fruit.description
        binding.valueDetailFamily.text = fruit.origin
    }
}