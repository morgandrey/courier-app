package com.example.courierapp.views

import com.example.courierapp.models.Order
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


/**
 * Created by Andrey Morgunov on 14/03/2021.
 */

@AddToEndSingle
interface ActiveOrderListView : MvpView {
    fun onSuccessGetActiveOrders(orderList: List<Order>)
    fun switchLoading(show: Boolean)
    fun showError(message: String)
    fun showError(message: Int)
}