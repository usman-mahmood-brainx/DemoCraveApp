package com.example.demo_instaforfood.Fragments.MapFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_instaforfood.Models.Place
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.android.ui.IconGenerator


class MapFragment : Fragment() {
    lateinit var binding: FragmentMapBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    @SuppressLint("PotentialBehaviorOverride")
    private val callback = OnMapReadyCallback { googleMap ->

        val boundsBuilder = LatLngBounds.Builder()
        val iconGenerator = IconGenerator(requireContext())
        iconGenerator.setTextAppearance(R.style.myStyleTextMarker)
        iconGenerator.setBackground(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_custom_marker
            )
        )

        val placesList = listOf(
            Place("Johny and Jugnu", "Fast Food Chain", 31.27, 74.17),
            Place("Villa", "Restaurant", 31.516, 74.352)
        )

        var count = 0
        for (place in placesList) {
            count++
            val latling = LatLng(place.latitude, place.longitude)
            boundsBuilder.include(latling)
            googleMap.addMarker(MarkerOptions().apply {
                position(latling)
                icon(BitmapDescriptorFactory.fromBitmap((iconGenerator.makeIcon(count.toString()))))
            })
            googleMap.setInfoWindowAdapter(MyInfoWindowAdapter(requireContext()))

        }
        var marker: Marker? = null
        googleMap.setOnMarkerClickListener {
            if (marker != null && marker?.id == it.id) {
                it.hideInfoWindow()
                marker = null
            } else if (marker == null) {
                marker = it
                it.showInfoWindow()
            }
            true
        }

//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),1000,1000,0))
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                boundsBuilder.build(),
                500,
                500,
                0
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMapBinding.inflate(inflater)

        // Bottom Sheet Setup
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.halfExpandedRatio = 0.6f
        binding.ivMinimize.setOnClickListener {

            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_HALF_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

        }

        // Bottom Sheet Results
        val foodResultAdapter = FoodResultAdapter(emptyList())
        binding.rvFoodResult.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFoodResult.adapter = foodResultAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


}