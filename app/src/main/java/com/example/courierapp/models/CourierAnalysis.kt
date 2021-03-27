package com.example.courierapp.models


/**
 * Created by Andrey Morgunov on 17/03/2021.
 */

data class CourierAnalysis(
    var countOfOrders: Long,
    var totalSum: Double,
    var ratingList: Map<Long, Long>
)