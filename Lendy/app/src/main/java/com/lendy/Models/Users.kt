package com.lendy.Models

import java.io.Serializable

class Users : Serializable
{
    var _id: String? = null
    var address: String? = null
    var lastname: String? = null
    var password: String? = null
    var firstname: String? = null
    var username: String? = null
    var type: String? = null
    //var isDriver: Boolean? = null
    var status: String? = null
    var cars: String? = null
    var lastConnection: String? = null
    var car: Car? = null
    var ratings: ArrayList<Rating>? = null
    var location: Any? = null
    var sex: String? = null
    var createdAt: String? = null
    var rating: Int? = null
    var fullName: String? = null
    var id: String? = null
}

