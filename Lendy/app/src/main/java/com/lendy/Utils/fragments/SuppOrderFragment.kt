package com.lendy.Utils.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.lendy.Manager.DataManager
import com.lendy.Manager.ServiceProvider
import com.lendy.Models.LocationObj
import com.lendy.R
import com.lendy.Utils.DataUtils
import com.lendy.Utils.DialogResult
import com.lendy.Utils.custom_views.ChooseDateDialog
import kotlinx.android.synthetic.main.supp_order_fragment.*
import java.util.*


class SuppOrderFragment : AppCompatActivity() {
    private var currentView: View? = null
    private var dialogResult: DialogResult? = null
    private var userId: String = ""
    private var dateTimestamp: Long = 0
    private var placesClient: PlacesClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.supp_order_fragment)

        initPlaces()

        val extras = intent.extras
        if (extras != null) {
            userId = extras.getString("userId")
        }

        dialogResult = object : DialogResult {
            override fun getHourResult(hour: Int, minute: Int) {
            }

            override fun getDialogResult(date: String, index: Int, dateTime: Long) {
                choose_date_supp.setText(date)
                dateTimestamp = dateTime
            }
        }

        date_supp.setOnClickListener {
            val datedialog = ChooseDateDialog(this, dialogResult, 1)
            datedialog.show()
        }

        hour_supp.setOnClickListener {
            DataUtils.showHourPicker(this, dialogResult = object : DialogResult {
                override fun getHourResult(hour: Int, minute: Int) {
                    choose_hour.setText(hour.toString() + "h" + minute.toString())
                }

                override fun getDialogResult(date: String, index: Int, dateTime: Long) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }

        valider.setOnClickListener {

            if ((choose_place.text == null || choose_place.text.toString().equals("")) &&
                    (choose_date_supp.text == null || choose_date_supp.text.toString().equals("")) &&
                    (choose_hour.text == null || choose_hour.text.toString().equals(""))) {
                Toast.makeText(this, "Vous devez renseigner tous les champs avant de valider", Toast.LENGTH_SHORT).show()
            } else {

                val locationObj = LocationObj()
                locationObj.address = choose_place.text.toString()
                locationObj.latitude = 48.856977499999999
                locationObj.longitude = 2.3296994

                ServiceProvider.addReservation(this, DataManager.SharedData.token, userId,
                        dateTimestamp.toString(), dateTimestamp.toString(), locationObj,
                        (System.currentTimeMillis() / 1000).toString(),
                        callback = { code, msg ->
                            if (code == 200) {
                                this.runOnUiThread {
                                    this.showDialog("Votre demande de rendez-vous à été envoyée !")
                                    Handler().postDelayed(Runnable {
                                        finish()
                                        this.onBackPressed()
                                    }, 2000)
                                }
                            }
                        });
            }
        }
    }

    fun initPlaces()
    {
        if (!Places.isInitialized())
            Places.initialize(this, "AIzaSyBTfZuvJ8vp6yHyZTnqYy2SqEtwO8C_N5c");

        placesClient = Places.createClient(this)

        // Initialize the AutocompleteSupportFragment.
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?

// Specify the types of place data to return.
        // Specify the types of place data to return.
        autocompleteFragment!!.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME))

// Set up a PlaceSelectionListener to handle the response.
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.e("Place", "Place: " + place.name + ", " + place.id)
            }

            override fun onError(p0: Status) {
                Log.e("Error", "Place: " + p0)
            }
        })
    }

    fun showDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setCancelable(true)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }
}