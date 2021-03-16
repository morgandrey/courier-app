package com.example.courierapp.presentation

import com.example.courierapp.R
import com.example.courierapp.remote.NetworkService
import com.example.courierapp.remote.OrderService
import com.example.courierapp.views.OrderListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import retrofit2.HttpException


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

class OrderListPresenter : MvpPresenter<OrderListView>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var orderService: OrderService

    fun getAvailableOrders(courierId: Long) {
        viewState.switchLoading(true)
        orderService = NetworkService.orderService
        compositeDisposable.add(
            orderService.getAvailableOrders(courierId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { orderList ->
                        viewState.switchLoading(false)
                        viewState.onSuccessGetOrders(orderList)
                    },
                    { error ->
                        viewState.switchLoading(false)
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

    fun getActiveOrders(courierId: Long) {
        viewState.switchLoading(true)
        orderService = NetworkService.orderService
        compositeDisposable.add(
            orderService.getActiveOrders(courierId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { orderList ->
                        viewState.switchLoading(false)
                        viewState.onSuccessGetOrders(orderList)
                    },
                    { error ->
                        viewState.switchLoading(false)
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

    fun getHistoryOrders(courierId: Long) {
        viewState.switchLoading(true)
        orderService = NetworkService.orderService
        compositeDisposable.add(
            orderService.getHistoryOrders(courierId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { orderList ->
                        viewState.switchLoading(false)
                        viewState.onSuccessGetOrders(orderList)
                    },
                    { error ->
                        viewState.switchLoading(false)
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

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}