package com.example.demo_instaforfood.Fragments.ReviewsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_instaforfood.Models.MenuItemRating
import com.example.demo_instaforfood.Paging.ReveiwsImagesPagingAdapter
import com.example.demo_instaforfood.ViewModels.ReviewsViewModel
import com.example.demo_instaforfood.databinding.FragmentReviewsBinding


class ReviewsFragment : Fragment() {

    private lateinit var reviewsBinding: FragmentReviewsBinding
    private lateinit var appViewModel: ReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        reviewsBinding = FragmentReviewsBinding.inflate(inflater)

        MenuRatingsSetup()
        ReviewImagesSetup()


        return reviewsBinding.root
    }

    private fun MenuRatingsSetup() {
        val menuRatinglist = dummyMenuRatingList()

        val MenuRatingAdapter = MenuRatingAdapter(menuRatinglist)
        reviewsBinding.rvMenuRatings.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MenuRatingAdapter
        }
    }

    private fun dummyMenuRatingList(): List<MenuItemRating> {
        return listOf(
            MenuItemRating("+9",175),
            MenuItemRating("8",85),
            MenuItemRating("7",22),
            MenuItemRating("6",34),
            MenuItemRating("1 to 5",24)
        )
    }

    private fun ReviewImagesSetup() {
        val reveiwsImagesPagingAdapter = ReveiwsImagesPagingAdapter()
        reviewsBinding.rvReviewImages.apply {
            layoutManager = GridLayoutManager(requireContext(),3)
            adapter = reveiwsImagesPagingAdapter
        }

        appViewModel = ViewModelProvider(requireActivity()).get(ReviewsViewModel::class.java)
        appViewModel.list.observe(requireActivity(),{
            reveiwsImagesPagingAdapter.submitData(lifecycle,it)
        })

    }


}