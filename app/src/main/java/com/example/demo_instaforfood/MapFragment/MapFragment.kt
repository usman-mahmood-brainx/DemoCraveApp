package com.example.demo_instaforfood.MapFragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.demo_instaforfood.Models.Place
import com.example.demo_instaforfood.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.ui.IconGenerator


class MapFragment : Fragment(),OnInfoWindowClickListener {


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         */

        val boundsBuilder =  LatLngBounds.Builder()
        val iconGenerator = IconGenerator(requireContext())
        iconGenerator.setTextAppearance(R.style.myStyleTextMarker)
        iconGenerator.setColor(ContextCompat.getColor(requireContext(),R.color.blue))

        val placesList = listOf(
            Place("Johny and Jugnu","Fast Food Chain",31.27,74.17),
            Place("Villa","Restaurant",31.516,74.352)
        )
        var count = 0
        for (place in placesList) {
            count++
            val latling = LatLng(place.latitude, place.longitude)
            boundsBuilder.include(latling)
            googleMap.addMarker(MarkerOptions().apply {
                position(latling)
                title(place.title)
                snippet(place.Description)
                icon(BitmapDescriptorFactory.fromBitmap((iconGenerator.makeIcon(count.toString()))))
            })

        }

        // Add a marker in Sydney and move the camera
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),1000,1000,0))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),500,500,0))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onInfoWindowClick(p0: Marker) {
        
    }


}