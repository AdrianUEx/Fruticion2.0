package com.example.fruticion.Fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fruticion.model.Fruit
import com.example.fruticion.R

class SearchAdapter(private val fruitList: List<Fruit>, private val onClickListener: (Fruit)->Unit) : RecyclerView.Adapter<SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(layoutInflater.inflate(R.layout.search_item_list, parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = fruitList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }
}