package com.example.courierapp.views

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


/**
 * Created by Andrey Morgunov on 17/03/2021.
 */

@AddToEndSingle
interface AnalysisView : MvpView {
    fun onSuccessSetData()
}