package com.lendy.Controllers

import android.Manifest
import android.app.AlertDialog
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
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.lendy.R
import android.support.v7.widget.DividerItemDecoration
import com.lendy.Utils.DataUtils.Companion.addFragmentToActivity
import com.lendy.Utils.adapters.RecyclerAdapter
import com.lendy.Utils.custom_views.BottomNavigationViewHelper
import com.lendy.Utils.fragments.*

enum class Keys(val key: String) {
    PROPOSE("propose"),
    SEARCH("search")
}

class MainActivity : AppCompatActivity() {
    var listOfFragment = arrayListOf<Any>()
    var listOfDrivers = arrayListOf<Any>()
    var listOfLocations = arrayListOf<JsonObject>()
    var mapFragment: MapFragment? = null
    var profileDetailFragment: ProfileDetailFragment? = null
    var contractFragment: ContractFragment? = null
    var messageFragment: MessagesFragment? = null
    var suppOrderFragment: SuppOrderFragment? = null
    var conversationFragment: ConversationFragment? = null
    var homeFragment: HomeFragment? = null
    var reservationFragment: ReservationFragment? = null
    var myProfilFragment: MyProfilFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFragment = HomeFragment()
        addFragmentToActivity(fragmentManager, homeFragment, R.id.activity_main)

        checkPermission()
        //initPubnub()

        bottom_navigation.setOnNavigationItemSelectedListener(
                object : BottomNavigationView.OnNavigationItemSelectedListener {
                    override fun onNavigationItemSelected(item: MenuItem): Boolean {

                        if (mapFragment != null && mapFragment?.isVisible!!)
                            removeMapFragment()

                        if (reservationFragment != null && reservationFragment?.isVisible!!)
                            removeReservationFragment()

                        if (messageFragment != null && messageFragment?.isVisible!!)
                            removeMessagesFragment()

                        if (conversationFragment != null && conversationFragment?.isVisible!!)
                            removeConversationFragment()

                        if (contractFragment != null && contractFragment?.isVisible!!)
                            removeContractFragment()


                        if (homeFragment != null && homeFragment?.isVisible!!)
                            removeHomeFragment()

                        if (myProfilFragment != null && myProfilFragment?.isVisible!!)
                            removeMyProfilFragment()

                        when (item.itemId) {
                            R.id.action_ongle_1 -> {
                                homeFragment = HomeFragment()
                                addFragmentToActivity(fragmentManager, homeFragment, R.id.activity_main)
                            }
                            R.id.action_ongle_3 -> {

                                /*val bundle = Bundle()

                                bundle.putString("arrayLocations", Gson().toJson(listOfLocations))

                                mapFragment = MapFragment()
                                // set LegalsFragment Arguments
                                mapFragment!!.arguments = bundle

                                addFragmentToActivity(fragmentManager, mapFragment, R.id.activity_main)*/

                                reservationFragment = ReservationFragment()

                                addFragmentToActivity(fragmentManager, reservationFragment, R.id.activity_main)
                            }
                            R.id.action_ongle_4 -> {
                                val bundle = Bundle()

                                //bundle.putSerializable("arrayLocations", listOfLocations)

                                messageFragment = MessagesFragment()
                                // set LegalsFragment Arguments
                                //researchFragment!!.arguments = bundle

                                addFragmentToActivity(fragmentManager, messageFragment, R.id.activity_main)
                            }
                            R.id.action_ongle_5 -> {
                                myProfilFragment = MyProfilFragment()
                                // set LegalsFragment Arguments
                                //researchFragment!!.arguments = bundle

                                addFragmentToActivity(fragmentManager, myProfilFragment, R.id.activity_main)

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

        // Désactive l'animation sur la BottomNavigationBar
        BottomNavigationViewHelper.disableShiftMode(bottom_navigation)
    }

    fun showDialog(message: String)
    {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setCancelable(true)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun hideBottomNavigation() {
        bottom_navigation.visibility = View.GONE
    }

    fun showBottomNavigation() {
        bottom_navigation.visibility = View.VISIBLE
    }

    public fun getDriversOrPassenger(users: ArrayList<Users>?, type: String?): ArrayList<Users>? {
        var array: ArrayList<Users>? = arrayListOf()
        listOfLocations = arrayListOf()

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

    fun removeReservationFragment() {
        if (this.reservationFragment != null) {
            removeFragment(reservationFragment)
            this.reservationFragment = null
        }
    }

    fun removeProfileDetailFragment() {
        if (this.profileDetailFragment != null) {
            removeFragment(profileDetailFragment)
            this.profileDetailFragment = null
        }
    }

    fun removeMessagesFragment() {
        if (this.messageFragment != null) {
            removeFragment(messageFragment)
            this.messageFragment = null
        }
    }

    fun removeConversationFragment() {
        if (this.conversationFragment != null) {
            removeFragment(conversationFragment)
            this.conversationFragment = null
        }
    }

    fun removeContractFragment() {
        if (this.contractFragment != null) {
            removeFragment(contractFragment)
            this.contractFragment = null
        }
    }

    fun removeHomeFragment() {
        if (this.homeFragment != null) {
            removeFragment(homeFragment)
            this.homeFragment = null
        }
    }

    fun removeMyProfilFragment() {
        if (this.myProfilFragment != null) {
            removeFragment(myProfilFragment)
            this.myProfilFragment = null
        }
    }

    private fun initPubnub() {
        val pnConfiguration = PNConfiguration()
        pnConfiguration.subscribeKey = "sub-c-32cb3dba-f4bc-11e8-aba4-3a82e8287a69"
        pnConfiguration.publishKey = "pub-c-81996e83-1edd-4f6b-b9a5-bbec2afb454f"
        pnConfiguration.isSecure = true
        val pubnub = PubNub(pnConfiguration)
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

        if (conversationFragment != null) {
            fragmentManager.popBackStack()
            showBottomNavigation()
        }
        else {
            if (reservationFragment != null || mapFragment != null || profileDetailFragment != null || messageFragment != null || myProfilFragment != null || contractFragment != null) {
                fragmentManager.popBackStack()
                showBottomNavigation()
                bottom_navigation.selectedItemId = R.id.action_ongle_1
            }
        }
    }

}
