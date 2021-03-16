package com.example.courierapp.presentation

import com.example.courierapp.views.OrderDetailsView
import moxy.MvpPresenter


/**
 * Created by Andrey Morgunov on 16/03/2021.
 */

class HistoryOrderDetailsPresenter : MvpPresenter<OrderDetailsView.History>()  {

    fun loadView() {
        viewState.loadView()
    }
}