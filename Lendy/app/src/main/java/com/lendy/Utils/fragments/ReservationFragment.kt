package com.lendy.Utils.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lendy.R
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lendy.Controllers.MainActivity
import com.lendy.Manager.DataManager
import com.lendy.Models.Reservation
import com.lendy.Models.ReservationState
import com.lendy.Utils.adapters.RecyclerAdapter
import com.lendy.Utils.adapters.ReservationAdapter
import kotlinx.android.synthetic.main.fragment_reservation.*
import kotlinx.android.synthetic.main.home_fragment.*


class ReservationFragment : Fragment()
{
    private var arrayListOfPassed: ArrayList<Reservation> = arrayListOf()
    private var arrayListOfNow: ArrayList<Reservation> = arrayListOf()
    private var arrayListOfPending: ArrayList<Reservation> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_reservation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getReservation()
        initListener()
    }

    private fun initListener()
    {
        activity_main_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.text!!.equals("En attente"))
                {
                    if (arrayListOfPending.isNullOrEmpty())
                        no_items.visibility = View.VISIBLE
                    else
                        no_items.visibility = View.GONE
                    updateAdapter(arrayListOfPending)
                }
                else if (tab.text!!.equals("En cours"))
                {
                    if (arrayListOfNow.isNullOrEmpty())
                        no_items.visibility = View.VISIBLE
                    else
                        no_items.visibility = View.GONE
                    updateAdapter(arrayListOfNow)
                }
                else if (tab.text!!.equals("Passées"))
                {
                    if (arrayListOfPassed.isNullOrEmpty())
                        no_items.visibility = View.VISIBLE
                    else
                        no_items.visibility = View.GONE
                    updateAdapter(arrayListOfPassed)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun initRecyclerView()
    {
        if (recyclerReservations != null) {
            recyclerReservations.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            //recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))

            if (arrayListOfPending.isNullOrEmpty())
                no_items.visibility = View.VISIBLE
            else
                no_items.visibility = View.GONE
            updateAdapter(arrayListOfPending)
        }
    }

    private fun updateAdapter(arrayOfReservation: ArrayList<Reservation>)
    {
        recyclerReservations.adapter = arrayOfReservation.let { ReservationAdapter(it, activity) }
    }

    private fun getReservation()
    {
        DataManager.getReservations(activity, DataManager.SharedData.token, callback = {
            if (it)
            {
                if (!DataManager.SharedData.sharedReservations.isNullOrEmpty()) {
                    if (no_items != null)
                        no_items.visibility = View.GONE

                    for (reservation: Reservation in DataManager.SharedData.sharedReservations!!) {

                        when (reservation.state)
                        {
                            ReservationState.PENDING -> arrayListOfPassed.add(reservation)
                            ReservationState.NOW -> arrayListOfNow.add(reservation)
                            ReservationState.PASSED -> arrayListOfPending.add(reservation)
                        }
                    }
                }
                else {
                    if (no_items != null)
                        no_items.visibility = View.VISIBLE
                }

                activity.runOnUiThread {
                    initRecyclerView()
                }
            }
            else
            {
                activity.runOnUiThread {
                    AlertDialog.Builder(activity)
                            .setTitle("Erreur")
                            .setMessage("Impossible d'afficher les réservations")
                            .setPositiveButton("OK", null)
                            .show()
                }
            }
        })
    }
}