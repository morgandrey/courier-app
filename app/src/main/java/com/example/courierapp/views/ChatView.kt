package com.example.courierapp.views

import com.example.courierapp.models.Message
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


/**
 * Created by Andrey Morgunov on 03/04/2021.
 */

@AddToEndSingle
interface ChatView : MvpView {
    fun onSuccessSaveMessage(message: Message)
    fun onSuccessGetMessageList(messageList: MutableList<Message>)
    fun showError(message: String)
    fun showError(message: Int)
}