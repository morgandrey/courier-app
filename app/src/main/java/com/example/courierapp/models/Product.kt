package com.example.courierapp.models

import com.google.gson.annotations.SerializedName


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

data class Product(
    @SerializedName("ProductName")
    var ProductName: String = "",
    @SerializedName("ProductDescription")
    var ProductDescription: String = "",
    @SerializedName("ProductCost")
    var ProductCost: Double = 0.0
) {
    override fun toString(): String {
        return "$ProductName, $ProductCost"
    }
}