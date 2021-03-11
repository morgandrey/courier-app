package com.example.courierapp.models

import com.google.gson.annotations.SerializedName


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

data class Courier(
    @SerializedName("courierId")
    var CourierId: Long = 0,
    @SerializedName("courierName")
    var CourierName: String = "",
    @SerializedName("courierSurname")
    var CourierSurname: String = "",
    @SerializedName("courierImage")
    var CourierImage: String? = null,
    @SerializedName("courierPhone")
    var CourierPhone: String = "",
    var CourierPassword: String? = null,
    var CourierSalt: String? = null,
    @SerializedName("courierRating")
    var CourierRating: Long = 0,
    @SerializedName("courierMoney")
    var CourierMoney: Double = 0.0
)
