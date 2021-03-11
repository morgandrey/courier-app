package com.example.courierapp.models


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

data class Courier(
    var CourierId: Long = 0,
    var CourierName: String = "",
    var CourierSurname: String = "",
    var CourierImage: String? = null,
    var CourierPhone: String = "",
    var CourierPassword: String? = null,
    var CourierSalt: String? = null,
    var CourierRating: Long = 0,
    var CourierMoney: Double = 0.0
)
