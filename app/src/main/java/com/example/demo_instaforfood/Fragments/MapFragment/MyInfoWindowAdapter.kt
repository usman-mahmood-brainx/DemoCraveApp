package com.example.demo_instaforfood.Fragments.MapFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.demo_instaforfood.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class MyInfoWindowAdapter(val mContext: Context) : GoogleMap.InfoWindowAdapter {
    var mWindow: View = LayoutInflater.from(mContext).inflate(R.layout.dialog_marker_info_window, null)

    private fun setInfoWindowText(marker: Marker) {
        val title = marker.title
        val tvTitle = mWindow.findViewById<TextView>(R.id.tvTitle)
       



    }

    override fun getInfoWindow(p0: Marker): View {
        setInfoWindowText(p0)
        return mWindow
    }

    override fun getInfoContents(p0: Marker): View {
        setInfoWindowText(p0)
        return mWindow
    }
}