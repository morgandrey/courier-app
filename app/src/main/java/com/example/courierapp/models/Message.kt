package com.example.courierapp.models

import com.google.gson.annotations.SerializedName


/**
 * Created by Andrey Morgunov on 03/04/2021.
 */

data class Message(
    var UserName: String = "",
    @SerializedName("messageDate")
    var MessageDate: String,
    @SerializedName("messageInput")
    var MessageInput: String = "",
    @SerializedName("messageFrom")
    var MessageFrom: String = "",
    @SerializedName("messageTo")
    var MessageTo: String = "",
    @SerializedName("idChatClient")
    var IdChatClient: Long? = null,
    @SerializedName("idOrder")
    var IdOrder: Long = 0
)
