package com.example.demo_instaforfood.Fragments.ReviewsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_instaforfood.Paging.ReveiwsImagesPagingAdapter
import com.example.demo_instaforfood.ViewModels.ReviewsViewModel
import com.example.demo_instaforfood.databinding.FragmentReviewsBinding


class ReviewsFragment : Fragment() {

    private lateinit var reviewsBinding: FragmentReviewsBinding
    private lateinit var reviewsViewModel: ReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        reviewsBinding = FragmentReviewsBinding.inflate(inflater)
        reviewsViewModel = ViewModelProvider(requireActivity()).get(ReviewsViewModel::class.java)

        MenuRatingsSetup()
        ReviewImagesSetup()


        return reviewsBinding.root
    }

    private fun MenuRatingsSetup() {

        val menuRatingAdapter = MenuRatingAdapter(emptyList())
        reviewsBinding.rvMenuRatings.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuRatingAdapter
        }

        reviewsViewModel.menuRatingList.observe(requireActivity(),{
            menuRatingAdapter.setList(it)
        })
    }

    private fun ReviewImagesSetup() {
        val reveiwsImagesPagingAdapter = ReveiwsImagesPagingAdapter()
        reviewsBinding.rvReviewImages.apply {
            layoutManager = GridLayoutManager(requireContext(),3)
            adapter = reveiwsImagesPagingAdapter
        }

        reviewsViewModel.imagesList.observe(requireActivity(),{
            reveiwsImagesPagingAdapter.submitData(lifecycle,it)
        })

    }


}