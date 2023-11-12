package com.example.fruticion.activity

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
        binding.valueDetailCalories.text = fruit.calories.toString()
        binding.valueDetailFat.text= fruit.fat.toString()
        binding.valueDetailProtein.text= fruit.protein.toString()
        binding.valueDetailSugar.text= fruit.sugar.toString()
        binding.valueDetailCarbo.text = fruit.carbohydrates.toString()




    }
}