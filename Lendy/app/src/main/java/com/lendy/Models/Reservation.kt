package com.lendy.Models

class Reservation {
    var since: Double = 0.0
    var to: Double = 0.0
    var members: ArrayList<Member> = arrayListOf()
    var state: ReservationState? = null
    var meetingPlace: LocationObj? = null
    var meetingTime: Double = 0.0
    var isValidated: String = ""
}