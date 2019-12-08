package com.lendy.Manager

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.databind.DeserializationFeature
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import org.json.JSONObject
import junit.framework.TestCase
import com.fasterxml.jackson.databind.ObjectMapper
import com.lendy.Models.*
import com.lendy.Utils.DataUtils
import okhttp3.RequestBody
import org.json.JSONArray
import java.lang.reflect.Array


enum class Endpoints(val value: String) {
    REGISTER("/register"),
    LOGIN("/login"),
    GET_DRIVERS("/drivers"),
    GET_LENDERS("/lenders"),
    GET_MYSELF("/myself"),
    SEND_MESSAGE("/message"),
    GET_MESSAGE("/message"),
    DISCUSSIONS("/discussion"),
    GET_RESERVATION("/reservation")
}

enum class HTTPMethod(val value: String) {
    POST("post"),
    GET("get"),
    PUT("put")

}

class ServiceProvider() {

    companion object {
        var baseURL: String = ""
        var dataUtils = DataUtils()

        val client = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
        val gSonBuilder = GsonBuilder().create()

        init {
            // SERVEUR LOCAL
            //baseURL = "http://10.0.2.2:27031/api/users"
            //baseURL = "http://192.168.1.12:27031/api/users"


            // SERVEUR LOCAL DE AZIZ
            baseURL = "http://api.lendy.fr:27031/api/users"

            // SERVEUR DE BILLEL
            //baseURL = "http://api.lendy.fr:27031/api/users"
        }

        fun getRequest(httpMethod: HTTPMethod, endpoint: Endpoints, queryParameters: java.util.ArrayList<java.util.HashMap<String, String>>?, bodyParams: java.util.HashMap<String, Any>?, token: String?): Request? {

            var request: Request? = null
            var url = baseURL + endpoint.value
            val urlBuilder = HttpUrl.parse(url)!!.newBuilder()

            if (urlBuilder != null && queryParameters != null) {

                for (aParameter in queryParameters) {
                    for ((key, value) in aParameter) {
                        urlBuilder.addQueryParameter(key, value)
                    }
                }

                url = urlBuilder.build().toString()
            }

            if (httpMethod == HTTPMethod.GET) {
                request = Request.Builder()
                        .url(url)
                        .addHeader("Authorization", "Bearer " + token)
                        .build()

            } else if (httpMethod == HTTPMethod.POST) {
                if (bodyParams != null) {
                    val JsonString = Gson().toJson(Gson().toJsonTree(bodyParams).asJsonObject)
                    val body = RequestBody.create(MediaType.parse("application/json"), JsonString)
                    request = Request.Builder()
                            .url(url)
                            .post(body)
                            .addHeader("Authorization", "Bearer " + token)
                            .build()
                }
            } else if (httpMethod == HTTPMethod.PUT) {
                if (bodyParams != null) {
                    val JsonString = Gson().toJson(Gson().toJsonTree(bodyParams).asJsonObject)
                    val body = RequestBody.create(MediaType.parse("application/json"), JsonString)
                    request = Request.Builder()
                            .url(url)
                            .post(body)
                            .build()
                }
            }
            return request
        }

        fun registerUser(context: Context?, user: User?, callback: ((code: Int?, newuser: User?) -> Unit)?) {
            //val queryParameter: HashMap<String, String> = hashMapOf()
            //queryParameter.put("access_token", "rUqrT1ZWTU842EFkuKBmnnZVOs26csuiU2iFoN3R3uwBA0MsyEqruwXIl4DxlB2O")

            var userRegistered: HashMap<String, Any> = hashMapOf()
            userRegistered["firstname"] = user?.firstname as Any
            userRegistered["lastname"] = user.lastname as Any
            userRegistered["username"] = user.username as Any
            userRegistered["password"] = user.password as Any
            userRegistered["type"] = user.type as Any

            // Request with Endpoint /users/anonymous and with current userId in Url parameters
            val request = ServiceProvider.getRequest(HTTPMethod.POST, Endpoints.REGISTER, null, userRegistered, null)
                    ?: return

            performRequest(request, context, callback = { response, exception ->
                val body_temp = response?.body()?.string()
                if (response != null && exception == null && response.code() == 200) {
                    val body = body_temp
                    val newUser = GsonBuilder().create().fromJson(body, User::class.java)
                    callback?.invoke(response.code(), newUser)
                } else
                    callback?.invoke(400, null)
            })
        }

        fun loginUser(context: Context?, email: String, password: String, callback: ((code: Int, user: User?) -> Unit)?) {
            val queryParameter: HashMap<String, Any> = hashMapOf()
            queryParameter["username"] = email
            queryParameter["password"] = password

            // Request with Endpoint /users/anonymous and with current userId in Url parameters
            val request = ServiceProvider.getRequest(HTTPMethod.POST, Endpoints.LOGIN, null, queryParameter, null)
                    ?: return

            performRequest(request, context, callback = { response, exception ->
                val body_temp = response?.body()?.string()
                if (response != null && exception == null && response.code() == 200) {
                    val body = body_temp
                    if (body != "\"emailPawssordInvalid\"") {
                        // DOIT RENVOYER UN TOKEN UNIQUEMENT !
                        val newUser = GsonBuilder().create().fromJson(body, User::class.java)
                        newUser.username = email
                        newUser.password = password
                        callback?.invoke(response.code(), newUser)
                    } else
                        callback?.invoke(400, null)
                } else
                    callback?.invoke(400, null)
            })
        }

        fun getDrivers(context: Context?, token: String, callback: ((code: Int, users: ArrayList<Users>?) -> Unit)?) {
            // Request with Endpoint /users/anonymous and with current userId in Url parameters
            val request = ServiceProvider.getRequest(HTTPMethod.GET, Endpoints.GET_DRIVERS, null, null, token)
                    ?: return

            performRequest(request, context, callback = { response, exception ->
                val body_temp = response?.body()?.string()
                if (response != null && exception == null && response.code() == 200) {
                    val body = body_temp
                    if (body != "Unauthorized") {
                        val jsonArray: JSONArray = JSONArray(body)
                        val arraylist: ArrayList<Users> = arrayListOf()
                        var objectMapper: ObjectMapper = ObjectMapper()
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                        for (i in 0 until jsonArray.length()) {
                            val obj = Gson().fromJson(jsonArray.optJSONObject(i).toString(), Users::class.java)
                            arraylist.add(obj)
                        }
                        callback?.invoke(response.code(), arraylist)

                    } else
                        callback?.invoke(401, null)
                } else
                    callback?.invoke(400, null)
            })
        }

        fun getLenders(context: Context?, token: String, callback: ((code: Int, users: ArrayList<Users>?) -> Unit)?) {
            // Request with Endpoint /users/anonymous and with current userId in Url parameters
            val request = ServiceProvider.getRequest(HTTPMethod.GET, Endpoints.GET_LENDERS, null, null, token)
                    ?: return

            performRequest(request, context, callback = { response, exception ->
                val body_temp = response?.body()?.string()
                if (response != null && exception == null && response.code() == 200) {
                    val body = body_temp
                    if (body != "Unauthorized") {
                        val jsonArray: JSONArray = JSONArray(body)
                        val arraylist: ArrayList<Users> = arrayListOf()
                        var objectMapper: ObjectMapper = ObjectMapper()
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                        for (i in 0 until jsonArray.length()) {
                            val obj = Gson().fromJson(jsonArray.optJSONObject(i).toString(), Users::class.java)
                            arraylist.add(obj)
                        }
                        callback?.invoke(response.code(), arraylist)

                    } else
                        callback?.invoke(401, null)
                } else
                    callback?.invoke(400, null)
            })
        }

        fun getMyself(context: Context?, token: String?, elementKey: String, elementValue: String, callback: ((code: Int, user: User?) -> Unit)?) {
            val bodyParameter: HashMap<String, Any> = hashMapOf()
            bodyParameter[elementKey] = elementValue
            // Request with Endpoint /users/anonymous and with current userId in Url parameters
            val request = ServiceProvider.getRequest(HTTPMethod.GET, Endpoints.GET_MYSELF, null, bodyParameter, token)
                    ?: return

            performRequest(request, context, callback = { response, exception ->
                if (response != null && exception == null && response.code() == 200) {
                    val body_temp = response?.body()?.string()
                    val newUser = GsonBuilder().create().fromJson(body_temp, User::class.java)
                    callback?.invoke(response.code(), newUser)
                } else
                    callback?.invoke(400, null)
            })
        }

        fun sendMessage(context: Context?, token: String?, message: String, contactId: String, callback: ((code: Int) -> Unit)?) {
            val queryParameter: HashMap<String, Any> = hashMapOf()
            queryParameter["message"] = message
            queryParameter["contacts"] = contactId

            // Request with Endpoint /users/anonymous and with current userId in Url parameters
            val request = ServiceProvider.getRequest(HTTPMethod.POST, Endpoints.SEND_MESSAGE, null, queryParameter, token)
                    ?: return

            performRequest(request, context, callback = { response, exception ->
                val body_temp = response?.body()?.string()
                if (response != null && exception == null && response.code() == 200) {
                    callback?.invoke(response.code())
                } else
                    callback?.invoke(400)
            })
        }

        fun getDiscussions(context: Context?, token: String?, callback: ((code: Int, discussions: ArrayList<Discussion>?) -> Unit)?) {

            // Request with Endpoint /users/anonymous and with current userId in Url parameters
            val request = ServiceProvider.getRequest(HTTPMethod.GET, Endpoints.DISCUSSIONS, null, null, token)
                    ?: return

            performRequest(request, context, callback = { response, exception ->
                val body_temp = response?.body()?.string()
                if (response != null && exception == null && response.code() == 200) {
                    val body = body_temp
                    val jsonArray: JSONArray = JSONArray(body)
                    val arraylist: ArrayList<Discussion> = arrayListOf()
                    var objectMapper: ObjectMapper = ObjectMapper()
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    for (i in 0 until jsonArray.length()) {
                        val obj = Gson().fromJson(jsonArray.optJSONObject(i).toString(), Discussion::class.java)
                        arraylist.add(obj)
                    }
                    callback?.invoke(response.code(), arraylist)
                } else
                    callback?.invoke(400, null)
            })
        }

        fun getReservations(context: Context?, token: String?, callback: ((code: Int, reservations: ArrayList<Reservation>?) -> Unit)?) {

            // Request with Endpoint /users/anonymous and with current userId in Url parameters
            val request = ServiceProvider.getRequest(HTTPMethod.GET, Endpoints.GET_RESERVATION, null, null, token)
                    ?: return

            performRequest(request, context, callback = { response, exception ->
                val body_temp = response?.body()?.string()
                if (response != null && exception == null && response.code() == 200) {
                    val body = body_temp
                    val jsonArray: JSONArray = JSONArray(body)
                    val arraylist: ArrayList<Reservation> = arrayListOf()
                    var objectMapper: ObjectMapper = ObjectMapper()
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    for (i in 0 until jsonArray.length()) {
                        val obj = Gson().fromJson(jsonArray.optJSONObject(i).toString(), Reservation::class.java)
                        arraylist.add(obj)
                    }
                    callback?.invoke(response.code(), arraylist)
                } else
                    callback?.invoke(400, null)
            })
        }

        fun getSpecificDiscussionMessages(context: Context?, token: String?, contactId: String, callback: ((code: Int, messages: ArrayList<Message>?) -> Unit)?) {

            val queryParameter: ArrayList<HashMap<String, String>> = arrayListOf()
            var hashmap = hashMapOf<String, String>()
            hashmap.put("contacts", contactId)
            queryParameter.add(hashmap)

            // Request with Endpoint /users/anonymous and with current userId in Url parameters
            val request = ServiceProvider.getRequest(HTTPMethod.GET, Endpoints.GET_MESSAGE, queryParameter, null, token)
                    ?: return

            performRequest(request, context, callback = { response, exception ->
                val body_temp = response?.body()?.string()
                if (response != null && exception == null && response.code() == 200) {
                    val body = body_temp
                    val jsonArray: JSONArray = JSONArray(body)
                    val arraylist: ArrayList<Message> = arrayListOf()
                    var objectMapper: ObjectMapper = ObjectMapper()
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    for (i in 0 until jsonArray.length()) {
                        val obj = Gson().fromJson(jsonArray.optJSONObject(i).toString(), Message::class.java)
                        arraylist.add(obj)
                    }
                    callback?.invoke(response.code(), arraylist)
                } else
                    callback?.invoke(400, null)
            })
        }


        // This method will perform request and return via callback any reponse and Exception
        // It will also Log all informations about current request
        fun performRequest(request: Request?, context: Context?, callback: ((response: Response?, exception: IOException?) -> Unit)?) {
            if (request == null || context == null) {
                return
            }

            Log.i("Info", "Performing request :\n Url: ${request.url()}\n Http method: ${request.method()}\nParams: ${request.body()}\n ---------------- \n")
            this.client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    Log.i("Info", "Request did succeed:\n Status code: ${response?.code()}\n Type: ${response?.body()?.contentType()}\n ---------------- \n")
                    callback?.invoke(response, null)
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    Log.i("Info", "Request did failed: 400")
                    Log.e("Info", "WS Exception ${e.toString()}")
                    callback?.invoke(null, e) // No connection error
                }
            })
        }
    }
}

// DataClass qui est retourné dans l'interface , la valeur de retour de la requete est stockée dans notre variable result
data class DataResult(val result: String?)
