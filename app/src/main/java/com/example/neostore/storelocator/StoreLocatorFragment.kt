/*
package com.example.neostore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neostore.R
import com.example.neostore.storelocator.StoreLocatorScreen
import com.example.neostore.adapter.StoreLocatorAdapter
import com.example.neostore.storelocator.StoreLocatorClass
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_store_locator.*
import kotlinx.android.synthetic.main.fragment_store_locator.view.*
import kotlinx.android.synthetic.main.registratio_actionbar_custom_layout.*


class StoreLocatorFragment : Fragment(),OnMapReadyCallback  {
    lateinit var storeNameList: MutableList<StoreLocatorClass>
    lateinit var map : GoogleMap
    lateinit var builder : LatLngBounds.Builder
    lateinit var bounds: LatLngBounds
    lateinit var markerList : MutableList<Marker>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_store_locator, container, false)

        val supportMapFragment : SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        view.storeLocatorTollbarBack.setOnClickListener(){
            (activity as StoreLocatorScreen).onBackPressed()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storeNameList = mutableListOf(
            StoreLocatorClass("NeoSOFT Technologies Rabale",
                "Unit No 501, Sigma IT Park, Plot No R-203,204, Midc TTC Industrial Area. Rabale, Navi Mumbai, Maharashtra 400701",
                19.1410544,
                73.0088433),
            StoreLocatorClass("NeoSOFT Technologies Parel",
                "4th Floor, The Ruby, 29, Senapati Bapat Marg, Dadar West, Mumbai, Maharashtra 400028",
                19.0174469,
                72.8275572),
            StoreLocatorClass("NeoSOFT Technologies Pune",
                "NTPL SEZ (Blueridge), IT6, 1st Floor, Rajiv Gandhi - Infotech Park, Phase-I, Hinjewadi, Pune, Maharashtra 411057",
                18.5607534,
                73.7314271
            ),
            StoreLocatorClass("NeoSOFT Technologies Prabhadevi",
                "8th, 9th & 10th Floor, Business Arcade Tower, Plot no- 584, Sayani Rd, Opp Parel Bus Depot, Dighe Nagar, Prabhadevi, Mumbai - 400 025, INDIA",
                19.01803,
                72.828343
            ),
            StoreLocatorClass("NeoSOFT Technologies Bengaluru",
                "91 Spring board,4th Floor, No. 22, 5th Block, Salarpuria Towers - I, Hosur Road, Koramangala Bengaluru -560095, INDIA",
                12.9248671,
                77.6318783
            ),
            StoreLocatorClass("NeoSOFT Technologies Ahmedabad",
                "21st floor, D block, Westgate, Sarkhej Gandhinagar Hwy, Near YMCA Club, Makarba, Ahmedabad, Gujarat - 380015, INDIA",
                23.0032836,
                72.4982642
            )
        )

        val storeAdapter = StoreLocatorAdapter(storeNameList)
        mapRecyclerview.adapter = storeAdapter
        mapRecyclerview.layoutManager =
            LinearLayoutManager((activity as StoreLocatorScreen).applicationContext,LinearLayoutManager.VERTICAL,false)

        storeAdapter.onSetClickListerner(object : StoreLocatorAdapter.onItemClickListerner {
            override fun onClickListerner(position: Int, lat: Double, lon: Double) {
                val cameraPosition = CameraPosition.Builder()
                    .target(LatLng(lat,lon))
                    .zoom(17f)
                    .build()
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                markerList[position].showInfoWindow()
            }
        })


    }


    override fun onMapReady(googleMap: GoogleMap) {
       map = googleMap
        builder = LatLngBounds.builder()
        markerList = mutableListOf()
        for(location in storeNameList){
            val marker = map.addMarker(MarkerOptions().position(LatLng(location.lat,location.lon))
                .title(location.name)
                .icon(BitmapDescriptorFactory.defaultMarker())
                .snippet(location.address)
            )
            builder.include(marker!!.position)
            markerList.add(marker)
        }
        bounds = builder.build()
        val width : Int = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding : Int = (width * 0.12).toInt()
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,width,height,padding))
    }
}

 */