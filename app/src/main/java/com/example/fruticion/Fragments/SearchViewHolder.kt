package com.example.fruticion.Fragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fruticion.model.Fruit
import com.example.fruticion.databinding.SearchItemListBinding

class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = SearchItemListBinding.bind(view)

    fun render (fruit: Fruit, onClickListener: (Fruit)->Unit) {
        binding.fruitName.text = fruit.name
        binding.fruitCountry.text = fruit.origin
        binding.fruitColor.text = fruit.color
        binding.cvItem.setOnClickListener { onClickListener(fruit) }
    }
}