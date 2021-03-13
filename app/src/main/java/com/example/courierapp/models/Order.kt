package com.example.courierapp.models

import com.google.gson.annotations.SerializedName


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

data class Order(
    @SerializedName("OrderId")
    var OrderId: Long = 0,
    @SerializedName("CourierId")
    var CourierId: Long = 0,
    @SerializedName("ClientName")
    var ClientName: String = "",
    @SerializedName("ClientSurname")
    var ClientSurname: String = "",
    @SerializedName("ClientPhone")
    var ClientPhone: String = "",
    @SerializedName("DeliveryAddress")
    var DeliveryAddress: String = "",
    @SerializedName("OrderDate")
    var OrderDate: String = "",
    @SerializedName("OrderDescription")
    var OrderDescription: String = "",
    @SerializedName("OrderStatusId")
    var OrderStatusId: Long = 0,
    @SerializedName("OrderRating")
    var OrderRating: Long = 0,
    @SerializedName("CourierReward")
    var CourierReward: Double = 0.0,
    @SerializedName("Products")
    var Products: List<Product>? = null
)