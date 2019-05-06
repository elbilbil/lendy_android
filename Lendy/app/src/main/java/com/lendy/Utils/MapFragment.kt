package com.lendy.Utils

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
import com.google.gson.JsonObject
import com.lendy.Controllers.MainActivity
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

        if (arguments.getSerializable("arrayLocations") != null)
            arrayListOfLocations = arguments.getSerializable("arrayLocations") as ArrayList<JsonObject>
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

            /*for(i in 0 until arrayListOfLocations.size)
            {
                val temp: JsonObject = arrayListOfLocations.get(i)
                val marker: MarkerOptions = MarkerOptions().position(LatLng(temp.get("latitude").asDouble, temp.get("longitude").asDouble)).title("Marker")
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                mMap.addMarker(marker)
            }*/

            //A SUPPRIMER
            val marker1: MarkerOptions = MarkerOptions().position(LatLng(43.295021, 5.374490)).title("Marker")
            val marker2: MarkerOptions = MarkerOptions().position(LatLng(43.3102998, 5.3676044)).title("Marker")
            val marker3: MarkerOptions = MarkerOptions().position(LatLng(43.304407, 5.374210)).title("Marker")
            val marker4: MarkerOptions = MarkerOptions().position(LatLng(43.295989, 5.376768)).title("Marker")
            marker1.icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
            marker2.icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
            marker3.icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
            marker4.icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
            mMap.addMarker(marker1)
            mMap.addMarker(marker2)
            mMap.addMarker(marker3)
            mMap.addMarker(marker4)
            val cameraPosition = CameraPosition.Builder().target(marker1.position).zoom(13f).build()
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