package com.example.demo_instaforfood.Paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo_instaforfood.Models.ReviewImage
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.TempModels.Data


class ReveiwsImagesPagingAdapter : PagingDataAdapter<Data, ReveiwsImagesPagingAdapter.ViewHolder>(COMPARATOR) {

    
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
        val data = getItem(position)

        Glide.with(holder.ivReview)
            .load(data?.avatar)
            .into(holder.ivReview)
//        holder.ivReview.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.noodels))

    }

    // Holds the views for adding it to image and text
    class ViewHolder(ReviewView: View) : RecyclerView.ViewHolder(ReviewView) {
        val ivReview: ImageView = ReviewView.findViewById(R.id.ivReviewImage)

    }
    companion object{
        private val COMPARATOR = object: DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }

        }
    }
}