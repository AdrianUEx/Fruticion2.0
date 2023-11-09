package com.example.fruticion.Fragments

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fruticion.model.Fruit
import com.example.fruticion.databinding.SearchItemListBinding

class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = SearchItemListBinding.bind(view)

    fun render (fruit: Fruit, onClickListener: (Fruit)->Unit) {
        binding.fruitName.text = fruit.name
        binding.fruitCountry.text = fruit.family
        binding.fruitColor.text = fruit.genus
        binding.cvItem.setOnClickListener { onClickListener(fruit) }
    }
}