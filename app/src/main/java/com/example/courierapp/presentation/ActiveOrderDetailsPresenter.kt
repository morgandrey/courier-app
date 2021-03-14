package com.example.courierapp.presentation

import com.example.courierapp.R
import com.example.courierapp.models.Order
import com.example.courierapp.remote.NetworkService
import com.example.courierapp.remote.OrderService
import com.example.courierapp.views.ActiveOrderDetailsView
import com.example.courierapp.views.AvailableOrderDetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import retrofit2.HttpException


/**
 * Created by Andrey Morgunov on 14/03/2021.
 */

class ActiveOrderDetailsPresenter : MvpPresenter<ActiveOrderDetailsView>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var orderService: OrderService

    fun completeOrder(order: Order) {
        orderService = NetworkService.orderService
        compositeDisposable.add(
            orderService.completeOrder(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { viewState.onSuccessCompleteOrder() },
                    { error ->
                        if (error is HttpException) {
                            val responseBody = error.response()?.errorBody()
                            viewState.showError(message = responseBody?.string().orEmpty())
                        } else {
                            viewState.showError(R.string.complete_order_error)
                        }
                    }
                )
        )
    }

    fun loadView() {
        viewState.loadView()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}