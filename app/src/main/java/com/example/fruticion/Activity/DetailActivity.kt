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

        //Familia, genero y orden
        binding.valueDetailFamily.text = fruit.family
        binding.valueDetailGenus.text = fruit.genus
        binding.valueDetailOrder.text = fruit.order
        //Informacion nutricional
        binding.valueDetailCalories.text = fruit.nutritions?.calories.toString()
        binding.valueDetailFat.text= fruit.nutritions?.fat.toString()
        binding.valueDetailProtein.text= fruit.nutritions?.protein.toString()
        binding.valueDetailSugar.text= fruit.nutritions?.sugar.toString()
        binding.valueDetailCarbo.text = fruit.nutritions?.carbohydrates.toString()




    }
}