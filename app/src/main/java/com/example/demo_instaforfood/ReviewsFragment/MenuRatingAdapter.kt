package com.example.demo_instaforfood.ReviewsFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_instaforfood.Models.MenuItemRating
import com.example.demo_instaforfood.R

class MenuRatingAdapter(private var ratingList: List<MenuItemRating>) : RecyclerView.Adapter<MenuRatingAdapter.ViewHolder>() {

    fun setList(list : List<MenuItemRating>){
        ratingList = list
        notifyDataSetChanged()
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item_rating_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val rating = ratingList[position]

        holder.tvTitle.text = rating.title
        holder.tvRating.text = rating.totalRating.toString()

        var maxRating = 250
        val mytotalrating = rating.totalRating
        if(mytotalrating >= maxRating ){
            maxRating = maxRating + 20
        }
        val progress = (mytotalrating.toDouble() / maxRating.toDouble() * 100).toInt()

        holder.progressBar.progress = progress
        
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return ratingList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(RatingView: View) : RecyclerView.ViewHolder(RatingView) {
        val tvTitle: TextView = RatingView.findViewById(R.id.tvTitle)
        val tvRating:TextView = RatingView.findViewById(R.id.tvTotalRating)
        val progressBar:ProgressBar = RatingView.findViewById(R.id.progressBar)


    }
}