package com.example.courierapp.remote

import com.example.courierapp.models.Courier
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


/**
 * Created by Andrey Morgunov on 12/03/2021.
 */

interface ProfileService {
    @PUT("api/couriers/{id}")
    fun updateCourierProfile(@Path("id") id: Long, @Body user: Courier): Observable<Response<Unit>>
    @GET("api/couriers/{id}")
    fun getCourier(@Path("id") courierId: Long): Observable<Courier>
}