package com.example.fruticion.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fruticion.R
import com.example.fruticion.model.Fruit

class WeeklyIntakeAdapter (private var weeklyFruitList: List<Fruit>, private val onClickListener: (Fruit)->Unit) : RecyclerView.Adapter<SearchViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(layoutInflater.inflate(R.layout.search_item_list, parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = weeklyFruitList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return  weeklyFruitList.size
    }

    fun updateList(fruits : List<Fruit>){
        this.weeklyFruitList = fruits
        notifyDataSetChanged()
    }
}