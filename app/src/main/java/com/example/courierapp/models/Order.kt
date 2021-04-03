package com.example.courierapp.models

import com.google.gson.annotations.SerializedName


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

data class Order(
    @SerializedName("orderId")
    var OrderId: Long = 0,
    @SerializedName("courierId")
    var CourierId: Long? = null,
    @SerializedName("clientName")
    var ClientName: String = "",
    @SerializedName("clientSurname")
    var ClientSurname: String = "",
    @SerializedName("clientPatronymic")
    var ClientPatronymic: String = "",
    @SerializedName("clientPhone")
    var ClientPhone: String = "",
    @SerializedName("deliveryAddress")
    var DeliveryAddress: String = "",
    @SerializedName("orderDate")
    var OrderDate: String = "",
    @SerializedName("orderDescription")
    var OrderDescription: String = "",
    @SerializedName("orderStatusId")
    var OrderStatusId: Long = 0,
    @SerializedName("orderRating")
    var OrderRating: Long = 0,
    @SerializedName("courierReward")
    var CourierReward: Double = 0.0,
    @SerializedName("products")
    var Products: List<Product>? = null
)