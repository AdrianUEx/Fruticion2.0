package com.example.fruticion.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fruticion.R
import com.example.fruticion.model.Fruit

class FavoriteAdapter(private var favFruitList: List<Fruit>, private val onClickListener: (Fruit)->Unit) : RecyclerView.Adapter<SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(layoutInflater.inflate(R.layout.search_item_list, parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = favFruitList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return  favFruitList.size
    }

    fun updateList(fruits : List<Fruit>){
        this.favFruitList = fruits
        notifyDataSetChanged()
    }
}