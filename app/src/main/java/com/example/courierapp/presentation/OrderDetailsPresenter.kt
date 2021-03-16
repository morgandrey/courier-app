package com.example.courierapp.presentation

import com.example.courierapp.R
import com.example.courierapp.models.Order
import com.example.courierapp.remote.NetworkService
import com.example.courierapp.remote.OrderService
import com.example.courierapp.views.OrderDetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import retrofit2.HttpException


/**
 * Created by Andrey Morgunov on 14/03/2021.
 */

class AvailableOrderDetailsPresenter : MvpPresenter<OrderDetailsView.Available>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var orderService: OrderService

    fun takeOrder(order: Order) {
        orderService = NetworkService.orderService
        compositeDisposable.add(
            orderService.takeOrder(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { viewState.onSuccessTakeOrder() },
                    { error ->
                        if (error is HttpException) {
                            val responseBody = error.response()?.errorBody()
                            viewState.showError(message = responseBody?.string().orEmpty())
                        } else {
                            viewState.showError(R.string.loading_orders_error)
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