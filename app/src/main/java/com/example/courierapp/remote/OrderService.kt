package com.example.courierapp.remote

import com.example.courierapp.models.Order
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

interface OrderService {
    @GET("api/available-orders/{courierId}")
    fun getAvailableOrders(@Path("courierId") courierId: Long): Observable<List<Order>>
    @POST("api/order/take")
    fun takeOrder(@Body order: Order): Observable<Response<Unit>>
    @POST("api/order/complete")
    fun completeOrder(@Body order: Order): Observable<Response<Unit>>
    @GET("api/courier/{courierId}/active-orders")
    fun getActiveOrders(@Path("courierId") courierId: Long): Observable<List<Order>>
    @GET("api/courier/{courierId}/history-orders")
    fun getHistoryOrders(@Path("courierId") courierId: Long): Observable<Response<List<Order>>>
}