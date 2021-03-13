package com.example.courierapp.remote


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

object NetworkService {
    private const val BASE_URL = "http://10.0.2.2:51104/"
    val authService: AuthService
        get() = RetrofitClient.getClient(BASE_URL).create(AuthService::class.java)
    val profileService: ProfileService
        get() = RetrofitClient.getClient(BASE_URL).create(ProfileService::class.java)
    val orderService: OrderService
        get() = RetrofitClient.getClient(BASE_URL).create(OrderService::class.java)
}