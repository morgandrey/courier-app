package com.example.courierapp.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


/**
 * Created by Andrey Morgunov on 14/03/2021.
 */

interface OrderDetailsView {
    @AddToEndSingle
    interface Active : MvpView {
        fun onSuccessCompleteOrder()
        fun onSuccessCancelOrder()
        fun loadView()
        fun showError(message: String)
        fun showError(message: Int)
    }
    @AddToEndSingle
    interface Available : MvpView {
        fun onSuccessTakeOrder()
        fun loadView()
        fun showError(message: String)
        fun showError(message: Int)
    }
    @AddToEndSingle
    interface History : MvpView {
        fun loadView()
    }
}