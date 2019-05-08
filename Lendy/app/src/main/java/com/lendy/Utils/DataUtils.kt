package com.lendy.Utils

import android.content.Context
import com.google.gson.Gson
import com.lendy.Models.User
import com.lendy.Models.Users
import com.lendy.R
import com.lmntrx.android.library.livin.missme.ProgressDialog

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
    }
}