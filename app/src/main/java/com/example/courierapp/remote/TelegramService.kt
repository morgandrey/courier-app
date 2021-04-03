package com.example.courierapp.remote

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * Created by Andrey Morgunov on 03/04/2021.
 */

interface TelegramService {
    @FormUrlEncoded
    @POST("sendMessage")
    fun sendMessage(@Field("chat_id") clientId: Long, @Field("text") message: String) : Observable<Unit>
}