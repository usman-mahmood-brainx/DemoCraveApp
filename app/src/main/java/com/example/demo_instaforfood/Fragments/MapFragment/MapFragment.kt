package com.example.demo_instaforfood.Fragments.MapFragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_instaforfood.Models.Place
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.Utils.Constants.HALF_EXPANDED
import com.example.demo_instaforfood.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.android.ui.IconGenerator
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap


class MapFragment : Fragment() {
    lateinit var binding: FragmentMapBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val  LOCATION_PERMISSION_REQUEST_CODE = 101
    private var locationPermission = MutableLiveData<Boolean>(false)
    private var marker: Marker? = null
    private lateinit var context:Context



    private val callback = OnMapReadyCallback { googleMap ->


        val boundsBuilder = LatLngBounds.Builder()
        val iconGenerator = IconGenerator(context)
        iconGenerator.setTextAppearance(R.style.myStyleTextMarker)
        iconGenerator.setBackground(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_custom_marker
            )
        )

        val placesList = dummyPlaceList()

        var count = 0
        for (place in placesList) {
            count++
            val latling = LatLng(place.latitude, place.longitude)
            boundsBuilder.include(latling)
            googleMap.addMarker(MarkerOptions().apply {
                position(latling)
                icon(BitmapDescriptorFactory.fromBitmap((iconGenerator.makeIcon(count.toString()))))

            })
            googleMap.setInfoWindowAdapter(MyInfoWindowAdapter(context))

        }

        googleMap.setOnMarkerClickListener {
            if (marker != null && marker?.id == it.id) {
                it.hideInfoWindow()
                marker = null
            } else if (marker == null) {
                marker = it
                it.showInfoWindow()
            }
            else{
                marker?.hideInfoWindow()
                marker = it
                it.showInfoWindow()
            }
            true
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        if(checkPermission()) {
            locationPermission.postValue(true)
         }
        else{
            getPermissions()
         }
        locationPermission.observe(requireActivity(),{ pemissionflag ->
            if(pemissionflag) {
                getCurrentLocation(googleMap)
            }
        })
    
    }

    private fun dummyPlaceList(): List<Place> {
        return listOf(
            Place("Johny and Jugnu", "Fast Food Chain", 31.27, 74.17),
            Place("Villa", "Restaurant", 31.516, 74.352)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = requireContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMapBinding.inflate(inflater)


        // Bottom Sheet Setup
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.apply {
            isFitToContents = false
            halfExpandedRatio = HALF_EXPANDED
        }

        binding.ivMinimize.setOnClickListener {

            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_HALF_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

        }

        // Bottom Sheet Results
        val foodResultAdapter = FoodResultAdapter(emptyList())
        binding.rvFoodResult.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = foodResultAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        
        mapFragment?.getMapAsync(callback)
    }

    private fun checkPermission():Boolean{
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun getCurrentLocation(googleMap: GoogleMap) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,100)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(10000)
            .setMaxUpdateDelayMillis(15000)
            .build()

        var previousMarker: Marker? = null

        // Get the last known location
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                object : com.google.android.gms.location.LocationCallback() {
                    override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                        locationResult.lastLocation?.let { location ->
                            val latLng = LatLng(location.latitude, location.longitude)
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                            previousMarker?.remove()
                            previousMarker = googleMap.addMarker(MarkerOptions().position(latLng).title("Current Location"))

                        }
                    }
                },
                null
            )
        }
        else{
            getPermissions()
        }

    }
    private fun getPermissions(){
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] === PackageManager.PERMISSION_GRANTED){
                    locationPermission.postValue(true)
                }
                else {
                    Toast.makeText(context, "Please Give Location Persmission", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }



}