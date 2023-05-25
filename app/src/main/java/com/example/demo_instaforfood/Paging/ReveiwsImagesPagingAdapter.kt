package com.example.demo_instaforfood.Paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.TemporaryModels.Data


class ReveiwsImagesPagingAdapter : PagingDataAdapter<Data, ReveiwsImagesPagingAdapter.ViewHolder>(COMPARATOR) {

    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)

        Glide.with(holder.ivReview)
            .load(data?.avatar)
            .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
            .into(holder.ivReview)

    }

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