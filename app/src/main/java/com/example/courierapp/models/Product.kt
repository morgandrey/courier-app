package com.example.courierapp.models

import com.google.gson.annotations.SerializedName


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

data class Product(
    @SerializedName("productName")
    var ProductName: String = "",
    @SerializedName("productDescription")
    var ProductDescription: String = "",
    @SerializedName("productPrice")
    var ProductPrice: Double = 0.0
) {
    override fun toString(): String {
        return "$ProductName, $ProductPrice"
    }
}