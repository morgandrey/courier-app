package com.example.courierapp.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

@AddToEndSingle
interface RegisterView : MvpView {
    fun onSuccessSignUp(isSignUp: Boolean)
    fun showError(message: String)
    fun showError(message: Int)
}