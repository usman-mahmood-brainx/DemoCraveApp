package com.example.demo_instaforfood.Fragments.CategoryFragment

import android.graphics.Color
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.TempModels.Item
import com.example.demo_instaforfood.databinding.FragmentCategoryBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater)
        
        val pieChart = binding.pieChart
       

        val unsortedList = listOf(
            Item("Bread", 40, Color.BLUE),
            Item("Cake", 30, Color.RED),
            Item("Coke", 60, Color.GREEN)
        )

        val itemList = unsortedList.sortedByDescending { it.value }
        var sum = 0
        val pieEntries = mutableListOf<PieEntry>()
        val colors = mutableListOf<Int>()

        for (item in itemList) {
            pieEntries.add(PieEntry(item.value.toFloat(), item.name))
            colors.add(item.color)
            sum += item.value
        }

        val dataSet = PieDataSet(pieEntries, "")
        dataSet.colors = colors
        dataSet.sliceSpace = 1f

        val data = PieData(dataSet)
        data.setValueTextSize(0f)
        data.setValueTextColor(Color.WHITE)

        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.isEnabled = false
        pieChart.isRotationEnabled = false
        pieChart.setTouchEnabled(false)

        pieChart.setHoleRadius(75f)
        

        pieChart.animateY(1000, Easing.EaseInOutQuad)
        pieChart.invalidate()

        binding.tvSum.text = "$sum"



        return binding.root

    }

}