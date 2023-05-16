package com.example.demo_instaforfood.Fragments

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_instaforfood.Models.ReviewImage
import com.example.demo_instaforfood.R

class ReveiwsImageAdapter(
    val context: Context,
    private var imagesList: List<ReviewImage>
) : RecyclerView.Adapter<ReveiwsImageAdapter.ViewHolder>() {

    fun setList(list : List<ReviewImage>){
        imagesList = list
        notifyDataSetChanged()
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_image_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val image = imagesList[position]

//        Glide.with(holder.ivReview)
//            .load("drawable/noodels.png")
//            .into(holder.ivReview)

        
        holder.ivReview.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.noodels))



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return imagesList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ReviewView: View) : RecyclerView.ViewHolder(ReviewView) {
        val ivReview: ImageView = ReviewView.findViewById(R.id.ivReviewImage)



    }
}