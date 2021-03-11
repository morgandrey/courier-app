package com.example.courierapp.remote

import com.example.courierapp.models.Courier
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

interface AuthService {
    @POST("api/couriers/register")
    fun registerCourier(@Body courier: Courier): Observable<Boolean>

    @POST("api/couriers/login")
    fun loginCourier(@Body courier: Courier): Observable<Response<Courier>>
}