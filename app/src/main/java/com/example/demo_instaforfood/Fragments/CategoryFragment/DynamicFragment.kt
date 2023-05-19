package com.example.demo_instaforfood.Fragments.CategoryFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_instaforfood.databinding.FragmentDynamicBinding

class DynamicFragment : Fragment() {
    private var tabTitle: String? = null
    lateinit var binding: FragmentDynamicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tabTitle = it.getString(ARG_TAB_TITLE)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDynamicBinding.inflate(inflater)
        arguments?.let { args->
           val tabTitle = args.getString(ARG_TAB_TITLE)
        }
        val topRatedItemAdapter = TopRatedItemsAdapter(emptyList())
        binding.rvTopRated.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTopRated.adapter = topRatedItemAdapter

        return binding.root
    }

    companion object {
        private const val ARG_TAB_TITLE = "Name"
        private const val ARG_TOTAL_REVIEWS = "totalReviews"
        fun newInstance(name:String,totalReviews:Int) : DynamicFragment {
            val fragment = DynamicFragment()
            val args = Bundle()
            args.putString(ARG_TAB_TITLE,name)
            args.putInt(ARG_TOTAL_REVIEWS,totalReviews)
            fragment.arguments = args
            return fragment
        }
    }
}