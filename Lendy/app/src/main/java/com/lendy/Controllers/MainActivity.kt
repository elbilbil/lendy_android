package com.lendy.Controllers

import android.Manifest
import android.app.Fragment
import android.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.pubnub.api.PubNub
import com.pubnub.api.PNConfiguration
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lendy.Manager.DataManager
import com.lendy.Models.Users
import com.lendy.Utils.*
import org.json.JSONObject
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.lendy.R


enum class Keys(val key: String) {
    PROPOSE("propose"),
    SEARCH("search")
}

class MainActivity : AppCompatActivity() {
    var listOfFragment = arrayListOf<Any>()
    var listOfDrivers = arrayListOf<Any>()
    var listOfLocations = arrayListOf<JsonObject>()
    var mapFragment: MapFragment? = null
    var researchFragment: ResearchFragment? = null
    var profileDetailFragment: ProfileDetailFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()
        //initPubnub()

        bottom_navigation.setOnNavigationItemSelectedListener(
                object : BottomNavigationView.OnNavigationItemSelectedListener {
                    override fun onNavigationItemSelected(item: MenuItem): Boolean {

                        if (mapFragment != null && mapFragment?.isVisible!!)
                            removeMapFragment()

                        if (researchFragment != null && researchFragment?.isVisible!!)
                            removeResearchFragment()

                        when (item.itemId) {
                            R.id.action_ongle_1 -> {

                            }
                            R.id.action_ongle_2 -> {
                                val bundle = Bundle()

                                //bundle.putSerializable("arrayLocations", listOfLocations)

                                researchFragment = ResearchFragment()
                                // set LegalsFragment Arguments
                                //researchFragment!!.arguments = bundle

                                addFragmentToActivity(fragmentManager, researchFragment, R.id.activity_main)
                            }
                            R.id.action_ongle_3 -> {

                                val bundle = Bundle()

                                bundle.putSerializable("arrayLocations", listOfLocations)

                                mapFragment = MapFragment()
                                // set LegalsFragment Arguments
                                mapFragment!!.arguments = bundle

                                addFragmentToActivity(fragmentManager, mapFragment, R.id.activity_main)
                            }
                            R.id.action_ongle_4 -> {

                            }
                            R.id.action_ongle_5 -> {

                            }
                            else -> {

                            }
                        }
                        return true
                    }
                });


        /*else if (it.title.toString() == "Recherche")
        {
            removeResearchFragment()
            researchFragment = ResearchFragment()

            addFragmentToActivity(fragmentManager, researchFragment, R.id.activity_main)
        }*/

        //if (profileDetailFragment?.isVisible!!)
        //  removeProfileDetailFragment()


        if (DataUtils.readStringFromPreferences(this, "token") == "default")
            DataManager.SharedData.token = DataManager.SharedData.sharedUser?.token
        else {
            DataManager.SharedData.token = DataUtils.readStringFromPreferences(this, "token")
            DataManager.getMyself(this, DataManager.SharedData.token, "address", DataUtils.readStringFromPreferences(this, "address"), callback =
            { success ->
                if (success) {
                    if (DataManager.SharedData.sharedUser?.type == "preteur") {
                        DataManager.getDrivers(this, DataManager.SharedData.token, callback = { success, arrayUsers ->
                            if (success && arrayUsers != null) {
                                this.runOnUiThread {
                                    DataManager.SharedData.sharedDrivers?.let { recyclerInit("emprunteur", it) }
                                    messagetype.text = "Ils cherchent une voiture"
                                    // Load toutes les personnes qui recherchent une voiture et peupler le recyclerView de ces données
                                }
                            } else
                                Log.e("bad", "bad")
                        })

                    }
                    // Si le choix selectionné est "SEARCH"
                    else if (DataManager.SharedData.sharedUser?.type == "emprunteur") {
                        DataManager.getLenders(this, DataManager.SharedData.token, callback = { success, arrayUsers ->
                            if (success && arrayUsers != null) {
                                this.runOnUiThread {
                                    DataManager.SharedData.sharedLenders?.let { recyclerInit("preteur", it) }
                                    messagetype.text = "Ils proposent une voiture"
                                    // Load toutes les personnes qui proposent leur voiture et peupler le recyclerView de ces données également
                                }
                            } else
                                Log.e("bad", "bad")
                        })
                    }
                } else
                    Log.e("bad", "bad")
            })
        }
        // Désactive l'animation sur la BottomNavigationBar
        BottomNavigationViewHelper.disableShiftMode(bottom_navigation)
    }

    fun hideBottomNavigation() {
        bottom_navigation.visibility = View.GONE
    }

    fun showBottomNavigation() {
        bottom_navigation.visibility = View.VISIBLE
    }

    fun getDriversOrPassenger(users: ArrayList<Users>?, type: String?): ArrayList<Users>? {
        var array: ArrayList<Users>? = arrayListOf()

        var i = 0
        if (users == null || type == null)
            return array

        for (items in users) {
            if (items.type == type) {
                array?.add(items)
                val jsonObject = Gson().toJsonTree(items.location).getAsJsonObject()
                listOfLocations.add(jsonObject)
            }
        }
        return array
    }


    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//Can add more as per requirement
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    123)
        }
    }

    fun removeMapFragment() {
        if (this.mapFragment != null) {
            removeFragment(mapFragment)
            this.mapFragment = null
        }
    }

    fun removeResearchFragment() {
        if (this.researchFragment != null) {
            removeFragment(researchFragment)
            this.researchFragment = null
        }
    }

    fun removeProfileDetailFragment() {
        if (this.profileDetailFragment != null) {
            removeFragment(profileDetailFragment)
            this.profileDetailFragment = null
        }
    }

    fun recyclerInit(role: String?, users: ArrayList<Users>) {
        if (role == null)
            return
        // CAROUSEL DESACTIVATED
        //recyclerview.layoutManager = LinearManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Adapter changes cannot affect the size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        // Attach an Adapter to the recycleView who will contains the list of lootboxes and manage it
        recyclerView.adapter = getDriversOrPassenger(users, role)?.let { RecyclerAdapter(it, this) }

        /*recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val offset = recyclerView!!.computeHorizontalScrollOffset()
                if (offset % recyclerView!!.getChildAt(0).width === 0) {
                    currentPosition = offset / recyclerView!!.getChildAt(0).width
                }
            }
        })
*/
    }

    private fun initPubnub() {
        val pnConfiguration = PNConfiguration()
        pnConfiguration.subscribeKey = "sub-c-32cb3dba-f4bc-11e8-aba4-3a82e8287a69"
        pnConfiguration.publishKey = "pub-c-81996e83-1edd-4f6b-b9a5-bbec2afb454f"
        pnConfiguration.isSecure = true
        val pubnub = PubNub(pnConfiguration)
    }

    fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment?, frameId: Int) {

        if (fragment == null) {
            return;
        }

        val transaction = manager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss()
        listOfFragment.add(fragment)
    }

    fun removeFragment(fragment: Fragment?) {
        if (fragment == null || fragment.fragmentManager == null) {
            return
        }

        val transaction = fragment.fragmentManager.beginTransaction()
        transaction.remove(fragment)
        transaction.commitAllowingStateLoss()
        listOfFragment.remove(fragment)
    }

    override fun onBackPressed() {
        // DO Nothing
        if (mapFragment != null || profileDetailFragment != null || researchFragment != null) {
            fragmentManager.popBackStack()
            showBottomNavigation()
        } else
            finish()
    }

}
