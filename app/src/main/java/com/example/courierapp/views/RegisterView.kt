package com.example.courierapp.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

@AddToEndSingle
interface RegisterView : MvpView {
    @OneExecution
    fun onSuccessSignUp(isSignUp: Boolean)
    fun switchLoading(show: Boolean)
    fun showError(message: String)
    fun showError(message: Int)
}