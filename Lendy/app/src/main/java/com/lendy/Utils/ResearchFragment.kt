package com.lendy.Utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import com.jaredrummler.materialspinner.MaterialSpinner
import com.lendy.Controllers.*
import com.lendy.Manager.DataManager
import com.lendy.Models.User
import com.lendy.Models.Users
import com.lendy.R
import kotlinx.android.synthetic.main.register.*
import kotlinx.android.synthetic.main.research_fragment.*


interface DialogResult {
    fun getDialogResult(date: String, index: Int)
}

class ResearchFragment : Fragment() {

    var currentActivity: Activity? = null
    var currentView: View? = null
    var interfaceDialog: DialogResult? = null
    var itemSelected: Int? = null
    var itemSelected2: String? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.research_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity
        val cA = this.currentActivity
        interfaceDialog = object : DialogResult {
            override fun getDialogResult(date: String, index: Int) {
                if (index == 1)
                {
                    choose_date.setText(date)
                }
                else if (index == 2)
                {
                    choose_date2.setText(date)
                }
            }
        }

        var array :ArrayList<Int> = arrayListOf()
        array.add(1)
        array.add(2)
        array.add(3)
        array.add(4)
        voyageurspinner.setItems(array)
        voyageurspinner.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener<Int> { view, position, id, item -> itemSelected = item })

        var array2 :ArrayList<String> = arrayListOf()
        array2.add("Citadine")
        array2.add("Familiale")
        array2.add("Luxueuse")
        array2.add("Sportive")
        vehiculespinner.setItems(array2)
        vehiculespinner.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener<String> { view, position, id, item -> itemSelected2 = item })

        firstDate.setOnClickListener {
            val datedialog = ChooseDateDialog(activity, interfaceDialog, 1)
            datedialog.show()
        }

        secondDate.setOnClickListener {
            val datedialog = ChooseDateDialog(activity, interfaceDialog, 2)
            datedialog.show()
        }
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