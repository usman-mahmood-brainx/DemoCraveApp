package com.example.demo_instaforfood.Fragments.CategoryFragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_instaforfood.Models.Category
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.Utils.Constants.ZERO
import com.example.demo_instaforfood.ViewModels.CategoriesViewModel
import com.example.demo_instaforfood.ViewModels.ReviewsViewModel
import com.example.demo_instaforfood.databinding.FragmentCategoryBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.tabs.TabLayout

class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    lateinit var categoriesViewModel: CategoriesViewModel

    private val INITIAL_FRAGMENT_INDEX = ZERO
    private val SLICE_SPACE = 1f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater)
        categoriesViewModel = ViewModelProvider(requireActivity()).get(CategoriesViewModel::class.java)

       categoriesViewModel.categoryList.observe(requireActivity(),{categoryList ->
           pieChartSetup(categoryList)
           reviewsListSetup(categoryList)
           dynamicTabsSetup(categoryList)
       })


        return binding.root

    }

    private fun reviewsListSetup(categoryList: List<Category>) {
        val categoryAdapter = CategoryAdapter(categoryList)
        binding.apply {
            rvCategories.layoutManager = LinearLayoutManager(requireContext())
            rvCategories.adapter = categoryAdapter
            rvCategories.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    private fun pieChartSetup(categoryList: List<Category>) {

        val sortedCategoryList = categoryList.sortedByDescending { it.totalReviws }
        var sum = ZERO
        val pieEntries = mutableListOf<PieEntry>()
        val colorList = mutableListOf<Int>()

        for (category in sortedCategoryList) {
            pieEntries.add(PieEntry(category.totalReviws.toFloat(), category.name))
            colorList.add(Color.parseColor(category.color))
            sum += category.totalReviws
        }

        val dataSet = PieDataSet(pieEntries, "").apply {
            colors = colorList
            sliceSpace = SLICE_SPACE
        }

        val piedata = PieData(dataSet).apply {
            setValueTextSize(0f)
            setValueTextColor(Color.WHITE)
        }

        binding.pieChart.apply {
            data = piedata
            description.isEnabled = false
            setDrawEntryLabels(false)
            legend.isEnabled = false
            isRotationEnabled = false
            setTouchEnabled(false)
            setHoleRadius(75f)
            animateY(1000, Easing.EaseInOutQuad)
            invalidate()
        }

        binding.tvSum.text = "$sum"

    }

    private fun dynamicTabsSetup(categoryList: List<Category>) {

        val fragmentList = mutableListOf<DynamicFragment>()
        for (category in categoryList) {
            val tab = binding.tbItemList.newTab()
            tab.text = "${category.name} (${category.totalReviws})"
            binding.tbItemList.addTab(tab)
            val fragment = DynamicFragment.newInstance(category.name, category.totalReviws)
            fragmentList.add(fragment)
        }

        binding.tbItemList.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val selectedFragment = fragmentList[tab.position]
                replaceFragment(selectedFragment)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        val initialFragment = fragmentList[INITIAL_FRAGMENT_INDEX]
        replaceFragment(initialFragment)

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = childFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }



}