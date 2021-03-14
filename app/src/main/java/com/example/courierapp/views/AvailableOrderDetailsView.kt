package com.example.courierapp.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


/**
 * Created by Andrey Morgunov on 14/03/2021.
 */

@AddToEndSingle
interface AvailableOrderDetailsView : MvpView {
    fun onSuccessTakeOrder()
    fun loadView()
    fun showError(message: String)
    fun showError(message: Int)
}