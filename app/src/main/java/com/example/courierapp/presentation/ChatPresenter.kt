package com.example.courierapp.presentation

import com.example.courierapp.models.Message
import com.example.courierapp.remote.ChatService
import com.example.courierapp.remote.NetworkService
import com.example.courierapp.remote.TelegramService
import com.example.courierapp.views.ChatView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import retrofit2.HttpException


/**
 * Created by Andrey Morgunov on 03/04/2021.
 */

class ChatPresenter : MvpPresenter<ChatView>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var telegramService: TelegramService
    private lateinit var chatService: ChatService

    fun sendMessage(clientId: Long, message: String) {
        telegramService = NetworkService.telegramService
        compositeDisposable.add(
            telegramService.sendMessage(clientId, message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { },
                    { error ->
                        if (error is HttpException) {
                            val responseBody = error.response()?.errorBody()
                            viewState.showError(message = responseBody?.string().orEmpty())
                        } else {
                            viewState.showError("Telegram api error")
                        }
                    }
                )
        )
    }

    fun saveMessage(message: Message) {
        chatService = NetworkService.chatService
        compositeDisposable.add(
            chatService.saveMessage(message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { message -> viewState.onSuccessSaveMessage(message) },
                    { error ->
                        if (error is HttpException) {
                            val responseBody = error.response()?.errorBody()
                            viewState.showError(message = responseBody?.string().orEmpty())
                        } else {
                            viewState.showError("Save message error")
                        }
                    }
                )
        )
    }

    fun getMessageList(orderId: Long) {
        chatService = NetworkService.chatService
        compositeDisposable.add(
            chatService.getMessageList(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { messageList -> viewState.onSuccessGetMessageList(messageList)},
                    { error ->
                        if (error is HttpException) {
                            val responseBody = error.response()?.errorBody()
                            viewState.showError(message = responseBody?.string().orEmpty())
                        } else {
                            viewState.showError("Error")
                        }
                    }
                )
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}