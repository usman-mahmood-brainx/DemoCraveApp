package com.example.demo_instaforfood.Fragments.CategoryFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_instaforfood.Models.FoodResult
import com.example.demo_instaforfood.R

class TopRatedItemsAdapter(private var topRatedItemList: List<FoodResult>) : RecyclerView.Adapter<TopRatedItemsAdapter.ViewHolder>() {

    fun setList(list : List<FoodResult>){
        topRatedItemList = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rated, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 8
    }

    class ViewHolder(TopRatedItemsView: View) : RecyclerView.ViewHolder(TopRatedItemsView) {
        val tvName: TextView = TopRatedItemsView.findViewById(R.id.tvName)
        val tvRatingPlace: TextView = TopRatedItemsView.findViewById(R.id.tvRatingPlace)



    }
}