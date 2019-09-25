package com.lendy.Utils.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lendy.R
import kotlinx.android.synthetic.main.map_fragment.*
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lendy.Controllers.MainActivity
import com.lendy.Models.Message
import org.json.JSONObject

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var arrayListOfLocations = arrayListOf<JsonObject>()

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        //var sydney: LatLng = LatLng(-34.0, 151.0);
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    var currentActivity: Activity? = null
    var currentView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view: View? = inflater?.inflate(R.layout.map_fragment, container, false)

        val type = object : TypeToken<ArrayList<JsonObject>>() {}.type
        if (arguments.getString("arrayLocations") != null)
            arrayListOfLocations = Gson().fromJson(arguments.getString("arrayLocations"), type)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity
        val cA = this.currentActivity

        map.onCreate(savedInstanceState)

        map.onResume() // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(activity.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        map.getMapAsync(OnMapReadyCallback { mMap ->
            // DYNAMIC BUT FOR TEST , valeur set en DUR

            for(i in 0 until arrayListOfLocations.size)
            {
                val temp: JsonObject = arrayListOfLocations.get(i)
                val marker: MarkerOptions = MarkerOptions().position(LatLng(temp.get("latitude").asDouble, temp.get("longitude").asDouble)).title("")
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                mMap.addMarker(marker)
            }

            val cameraPosition = CameraPosition.Builder().target(LatLng(arrayListOfLocations.get(0).get("latitude").asDouble, arrayListOfLocations.get(0).get("longitude").asDouble)).zoom(13f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        })
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onSaveInstanceState(outState: Bundle?) {

    }

    override fun onDestroyView() {

        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun onSucceed() {

        val cA = this.currentActivity
    }

    fun onFail() {

        val cA = this.currentActivity
    }
}