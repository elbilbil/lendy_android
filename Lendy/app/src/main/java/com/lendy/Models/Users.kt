package com.lendy.Models

import org.json.JSONObject
import java.io.Serializable

class Users : Serializable
{
    var firstname: String? = null
    var lastname: String? = null
    var _id: String? = null
    var username: String? = null
    var password: String? = null
    var type: String? = null
    var role: String? = null
    var status: String? = null
    var cars: String? = null
    var location: Any? = null
    var sex: String? = null
    var createdAt: String? = null
}