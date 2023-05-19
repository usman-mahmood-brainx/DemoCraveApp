package com.example.demo_instaforfood.Fragments.CategoryFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_instaforfood.Models.Category
import com.example.demo_instaforfood.R

class CategoryAdapter(private var categoryList: List<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    fun setList(list : List<Category>){
        categoryList = list
        notifyDataSetChanged()
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val category = categoryList[position]
        holder.tvCategoryName.text = category.name
        val percentage = getPercentage(category.totalReviws)
        holder.tvTotalReviews.text = "${category.totalReviws} / ${percentage}% "
        
        val dotColorDrawable = holder.ivDotColor.background
        DrawableCompat.setTint(dotColorDrawable, category.color)
    }

    private fun getPercentage(catReview:Int): Int {
        var sum = 0
        for(category in categoryList){
           sum += category.totalReviws
        }
        
        val percentage = catReview.toDouble() / sum.toDouble()  * 100
        return percentage.toInt()


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return categoryList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(CategoryView: View) : RecyclerView.ViewHolder(CategoryView) {
        val tvCategoryName: TextView = CategoryView.findViewById(R.id.tvCategoryName)
        val ivDotColor:ImageView = CategoryView.findViewById(R.id.ivdotColor)
        val tvTotalReviews : TextView = CategoryView.findViewById(R.id.tvTotalReviews)

    }
}