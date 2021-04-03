package com.example.courierapp.models

import com.google.gson.annotations.SerializedName


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

data class Courier(
    @SerializedName("idCourier")
    var CourierId: Long = 0,
    @SerializedName("courierName")
    var CourierName: String = "",
    @SerializedName("courierSurname")
    var CourierSurname: String = "",
    @SerializedName("courierPatronymic")
    var CourierPatronymic: String = "",
    @SerializedName("courierImage")
    var CourierImage: String? = null,
    @SerializedName("courierPhone")
    var CourierPhone: String = "",
    @SerializedName("courierPassword")
    var CourierPassword: String = "",
    @SerializedName("courierSalt")
    var CourierSalt: String = "",
    @SerializedName("courierRating")
    var CourierRating: Double = 0.0,
    @SerializedName("courierRatingCount")
    var CourierRatingCount: Int = 0,
    @SerializedName("courierMoney")
    var CourierMoney: Double = 0.0
)
