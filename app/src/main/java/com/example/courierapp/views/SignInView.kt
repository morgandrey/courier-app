package com.example.courierapp.views

import com.example.courierapp.models.Courier
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

@AddToEndSingle
interface SignInView : MvpView {
    fun onSuccessSignIn(courier: Courier)
    fun showError(message: String)
    fun showError(message: Int)
}