package com.lendy.Manager

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.lendy.Models.Discussion
import com.lendy.Models.Message
import com.lendy.Models.User
import com.lendy.Models.Users
import com.lendy.Utils.DataUtils
import org.json.JSONArray
import javax.security.auth.callback.Callback

class DataManager
{
    @SuppressLint("StaticFieldLeak")
    object SharedData
    {
        var sharedContext: Context? = null
        var sharedUser: User? = null
        var sharedDrivers: ArrayList<Users>? = null
        var sharedLenders: ArrayList<Users>? = null
        var sharedDiscussions: ArrayList<Discussion>? = null
        var token: String? = null
    }

    companion object {

        fun checkIfUserExists(users: Array<Users>?, email: String?, password: String?) : Boolean
        {
            if (users == null || email == null || password == null)
                return false

            for (item in users)
            {
                if (item.username == email && item.password == password) {
                    updateSharedUser(item)
                    return true
                }
            }
            return false
        }

        fun updateSharedUser(user: Users?) {
            if (user == null) { return }
            val userSave = User()
            var firstname: String? = null
            var lastname: String? = null
            var username: String? = null
            var password: String? = null
            var type: String? = null
            var location: Any? = null
            var sex: String? = null
            var _id: String? = null

            userSave.firstname = user.firstname
            userSave.lastname = user.lastname
            userSave.username = user.username
            userSave.password = user.password
            userSave.type = user.type
            userSave.location = user.location
            userSave.sex = user.sex
            userSave._id = user._id
            SharedData.sharedUser = userSave
        }

        fun registerUser(context: Context?, user: User?, callback: (success: Boolean, newuser: User?) -> Unit)
        {
            if (context == null || user == null)
                return

            ServiceProvider.registerUser(context, user, callback = {code, newuser ->
                if (code != 200 && newuser == null)
                {
                    Log.e("FAILED", "CANT LOGIN")
                    callback.invoke(false, null)
                }
                else
                {
                    callback.invoke(true, newuser)
                }
            })
        }

        fun getDrivers(context: Context?, token: String?, callback: (success: Boolean, arrayOfUser: ArrayList<Users>?) -> Unit)
        {
            if (context == null || token == null)
                return

            ServiceProvider.getDrivers(context, token, callback = {code, arrayOfUser ->

                if (code != 200 && arrayOfUser == null)
                {
                    Log.e("FAILED", "CANT LOGIN")
                    callback.invoke(false, arrayOfUser)
                }
                else
                {
                    SharedData.sharedDrivers = arrayOfUser
                    callback.invoke(true, arrayOfUser)
                }
            } )
        }

        fun getLenders(context: Context?, token: String?, callback: (success: Boolean, arrayOfUser: ArrayList<Users>?) -> Unit)
        {
            if (context == null || token == null)
                return

            ServiceProvider.getLenders(context, token, callback = {code, arrayOfUser ->

                if (code != 200 && arrayOfUser == null)
                {
                    Log.e("FAILED", "CANT LOGIN")
                    callback.invoke(false, arrayOfUser)
                }
                else
                {
                    SharedData.sharedLenders = arrayOfUser
                    callback.invoke(true, arrayOfUser)
                }
            } )
        }

        fun getMyself(context: Context?, token: String?, key: String?, value:String?, callback: (success: Boolean) -> Unit)
        {
            if (context == null || key == null || value == null || token == null)
                return

            ServiceProvider.getMyself(context, token, key, value, callback = {code, user ->

                if (code != 200 && user == null)
                {
                    Log.e("FAILED", "CANT LOGIN")
                    callback.invoke(false)
                }
                else
                {
                    SharedData.sharedUser = user
                    callback.invoke(true)
                }
            } )
        }

        fun sendMessage(context: Context?, token: String?, message: String?, contactId: String?, callback: (success: Boolean) -> Unit)
        {
            if (context == null || token == null || message == null || contactId == null)
                return

            ServiceProvider.sendMessage(context, token,  message, contactId, callback = {code ->

                if (code != 200)
                {
                    Log.e("FAILED", "CANT LOGIN")
                    callback.invoke(false)
                }
                else
                {
                    callback.invoke(true)
                }
            } )
        }

        fun getDiscussions(context: Context?, token: String?, callback: (success: Boolean, discussions: ArrayList<Discussion>?) -> Unit)
        {
            if (context == null || token == null)
                return

            ServiceProvider.getDiscussions(context, token, callback = { code, discussions ->
                if (code == 200 && discussions != null)
                {
                    SharedData.sharedDiscussions = discussions
                    callback.invoke(true, discussions)
                }
                else
                {
                    callback.invoke(false, null)
                }
            })
        }

        fun getSpecificDiscussionMessages(context: Context?, token: String?, contactId: String?, callback: (success: Boolean, messages: ArrayList<Message>?) -> Unit)
        {
            if (context == null || token == null || contactId == null)
                return

            ServiceProvider.getSpecificDiscussionMessages(context, token, contactId, callback = { code, messages ->
                if (code == 200 && messages != null)
                {
                    callback.invoke(true, messages)
                }
                else
                {
                    callback.invoke(false, null)
                }
            })
        }


        fun loginUser(context: Context?, email: String?, password:String?, callback: (success: Boolean) -> Unit)
        {
            if (context == null || email == null || password == null)
                return

            ServiceProvider.loginUser(context, email, password, callback = {code, user ->

                if (code != 200 && user == null)
                {
                    Log.e("FAILED", "CANT LOGIN")
                    callback.invoke(false)
                }
                else
                {
                    SharedData.sharedUser = user
                    callback.invoke(true)
                }
            } )
        }
    }
}