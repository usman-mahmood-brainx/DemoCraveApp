package com.example.demo_instaforfood.Fragments.CategoryFragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_instaforfood.Models.Category
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.databinding.FragmentCategoryBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater)

        val categoryList = listOf(
            Category("Brunch",460,ContextCompat.getColor(requireContext(),R.color.lightBrown)),
            Category("Burgers", 61, Color.BLUE),
            Category("Pizza", 26, Color.RED),
            Category("Bangels", 3, Color.GREEN)
        )

        pieChartSetup(categoryList)



        val categoryAdapter = CategoryAdapter(categoryList)
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = categoryAdapter
        binding.rvCategories.setOverScrollMode(View.OVER_SCROLL_NEVER);




        return binding.root

    }

    private fun pieChartSetup(categoryList: List<Category>) {


        val sortedCategoryList = categoryList.sortedByDescending { it.totalReviws }
        var sum = 0
        val pieEntries = mutableListOf<PieEntry>()
        val colors = mutableListOf<Int>()

        for (category in sortedCategoryList) {
            pieEntries.add(PieEntry(category.totalReviws.toFloat(), category.name))
            colors.add(category.color)
            sum += category.totalReviws
        }

        val dataSet = PieDataSet(pieEntries, "")
        dataSet.colors = colors
        dataSet.sliceSpace = 1f

        val data = PieData(dataSet)
        data.setValueTextSize(0f)
        data.setValueTextColor(Color.WHITE)

        binding.pieChart.data = data
        binding.pieChart.description.isEnabled = false
        binding.pieChart.setDrawEntryLabels(false)
        binding.pieChart.legend.isEnabled = false
        binding.pieChart.isRotationEnabled = false
        binding.pieChart.setTouchEnabled(false)
        binding.pieChart.setHoleRadius(75f)

        binding.pieChart.animateY(1000, Easing.EaseInOutQuad)
        binding.pieChart.invalidate()

        binding.tvSum.text = "$sum"

    }

}