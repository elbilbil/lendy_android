package com.lendy.Utils

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson
import com.lendy.Models.User
import com.lendy.Models.Users
import com.lendy.R
import com.lmntrx.android.library.livin.missme.ProgressDialog
import android.app.TimePickerDialog
import android.widget.TimePicker
import java.util.*


class DataUtils {
    companion object {
        fun writeStringOnPreferences(c: Context?, key: String?, str: String?) {

            //Add to preference
            if (c != null) {
                val preferences = c.getSharedPreferences(
                        c.getString(R.string.com_lendy_preference_file_key), Context.MODE_PRIVATE)
                with(preferences.edit()) {
                    putString(key, str)
                    commit()
                }
            }
        }

        fun readStringFromPreferences(c: Context?, key: String?): String? {

            val preferences = c?.getSharedPreferences(
                    c.getString(R.string.com_lendy_preference_file_key), Context.MODE_PRIVATE)
            val encryptedString = preferences?.getString(key, "default")
            return encryptedString
        }

        fun hideSoftKeyboard(activity: Activity?) {
            val inputMethodManager = activity!!.getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (activity != null && inputMethodManager != null && activity.currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            }
        }

        fun writeinArrayOnPreferences(key: String?, array: Array<Users>?, c: Context) {

            val preferences = c.getSharedPreferences(
                    c.getString(R.string.com_lendy_preference_file_key), Context.MODE_PRIVATE)
            val editor = preferences.edit()

            val json: String = Gson().toJson(array);
            editor.putString(key, json);
            editor.commit();
        }

        fun progressShow(progressDialog: ProgressDialog?, message: String?, cancelable: Boolean?) {
            if (progressDialog == null || message == null || cancelable == null)
                return

            progressDialog.setMessage(message)
            progressDialog.setCancelable(cancelable)
            progressDialog.show()
        }

        fun progressHide(progressDialog: ProgressDialog?) {
            if (progressDialog == null)
                return

            progressDialog.dismiss()
        }

        fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment?, frameId: Int) {

            if (fragment == null) {
                return;
            }

            val transaction = manager.beginTransaction()
            transaction.add(frameId, fragment)
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss()
        }

        fun replaceFragment(manager: FragmentManager, fragment: Fragment?, frameId: Int) {

            if (fragment == null) {
                return;
            }

            val transaction = manager.beginTransaction()
            transaction.replace(frameId, fragment)
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss()
        }

        fun showHourPicker(activity: Activity?, dialogResult: DialogResult) {
            val myCalender = Calendar.getInstance()
            val hour = myCalender.get(Calendar.HOUR_OF_DAY)
            val minute = myCalender.get(Calendar.MINUTE)


            val myTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                if (view.isShown) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    myCalender.set(Calendar.MINUTE, minute)
                    dialogResult.getHourResult(hourOfDay, minute)

                }
            }
            val timePickerDialog = TimePickerDialog(activity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true)
            timePickerDialog.setTitle("Choose hour:")
            timePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            timePickerDialog.show()
        }
    }
}