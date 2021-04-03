package com.example.courierapp.remote


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

object NetworkService {

    private const val BASE_URL = "http://10.0.2.2:51104/"
    private const val TELEGRAM_URL = "https://api.telegram.org/bot1651293129:AAE_LRkh5lZq1LiWf9tml56CfjdpYVObEk8/"
    val authService: AuthService
        get() = RetrofitClient.getClient(BASE_URL).create(AuthService::class.java)
    val profileService: ProfileService
        get() = RetrofitClient.getClient(BASE_URL).create(ProfileService::class.java)
    val orderService: OrderService
        get() = RetrofitClient.getClient(BASE_URL).create(OrderService::class.java)
    val chatService: ChatService
        get() = RetrofitClient.getClient(BASE_URL)
            .create(ChatService::class.java)
    val telegramService: TelegramService
        get() = RetrofitClient.getClient(TELEGRAM_URL)
            .create(TelegramService::class.java)
}