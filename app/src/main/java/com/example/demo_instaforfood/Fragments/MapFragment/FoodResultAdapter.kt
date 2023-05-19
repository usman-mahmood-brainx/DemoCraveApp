package com.example.demo_instaforfood.Fragments.MapFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_instaforfood.Models.FoodResult
import com.example.demo_instaforfood.R

class FoodResultAdapter(private var foodResultList: List<FoodResult>) : RecyclerView.Adapter<FoodResultAdapter.ViewHolder>() {

    fun setList(list : List<FoodResult>){
        foodResultList = list
        notifyDataSetChanged()
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_result, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val foodResult = foodResultList[position]


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return 8
    }

    // Holds the views for adding it to image and text
    class ViewHolder(FoodResultView: View) : RecyclerView.ViewHolder(FoodResultView) {
        val tvName: TextView = FoodResultView.findViewById(R.id.tvName)



    }
}