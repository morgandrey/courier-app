package com.example.courierapp.remote

import com.example.courierapp.models.Message
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Created by Andrey Morgunov on 03/04/2021.
 */

interface ChatService {
    @POST("api/chat/send-message")
    fun saveMessage(@Body message: Message) : Observable<Message>

    @GET("api/chat/{orderId}")
    fun getMessageList(@Path("orderId") orderId: Long) : Observable<MutableList<Message>>
}