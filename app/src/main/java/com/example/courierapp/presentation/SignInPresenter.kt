package com.example.courierapp.presentation

import com.example.courierapp.R
import com.example.courierapp.models.Courier
import com.example.courierapp.remote.AuthService
import com.example.courierapp.remote.NetworkService
import com.example.courierapp.views.SignInView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import retrofit2.HttpException


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

class SignInPresenter : MvpPresenter<SignInView>() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var authService: AuthService

    fun signInCourier(courier: Courier) {
        authService = NetworkService.authService
        compositeDisposable.add(
            authService.loginCourier(courier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        if (response.code() == 204) {
                            viewState.showError(R.string.sign_in_incorrect_password_or_login)
                        } else {
                            viewState.onSuccessSignIn(response.body()!!)
                        }
                    },
                    { error ->
                        if (error is HttpException) {
                            val responseBody = error.response()?.errorBody()
                            viewState.showError(message = responseBody?.string().orEmpty())
                        } else {
                            viewState.showError(R.string.sign_in_error)
                        }
                    }
                )
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}