package com.example.demo_instaforfood.Fragments.MapFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_instaforfood.Models.FoodResult
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.Utils.Constants.FRAGMENT_COUNT

class FoodResultAdapter(private var foodResultList: List<FoodResult>) : RecyclerView.Adapter<FoodResultAdapter.ViewHolder>() {

    fun setList(list : List<FoodResult>){
        foodResultList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_result, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }


    override fun getItemCount(): Int {
        return FRAGMENT_COUNT
    }

    class ViewHolder(FoodResultView: View) : RecyclerView.ViewHolder(FoodResultView) {
        val tvName: TextView = FoodResultView.findViewById(R.id.tvName)



    }
}