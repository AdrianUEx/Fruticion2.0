package com.example.fruticion.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fruticion.model.Fruit
import com.example.fruticion.databinding.SearchItemListBinding

class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = SearchItemListBinding.bind(view)

    fun render (fruit: Fruit, onClickListener: (Fruit)->Unit) {
        with(binding){//Recordar que with() es para no poner el "binding" delante de cada linea dentro del with
            fruitName.text = fruit.name
            fruitFamily.text = fruit.family
            fruitOrder.text = fruit.order
            cvItem.setOnClickListener { onClickListener(fruit) }
        }

    }
}