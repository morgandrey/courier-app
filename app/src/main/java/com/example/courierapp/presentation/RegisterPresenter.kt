package com.example.courierapp.presentation

import com.example.courierapp.R
import com.example.courierapp.models.Courier
import com.example.courierapp.remote.AuthService
import com.example.courierapp.remote.NetworkService
import com.example.courierapp.views.RegisterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import retrofit2.HttpException


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

class RegisterPresenter : MvpPresenter<RegisterView>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var authService: AuthService

    fun signUpCourier(courier: Courier) {
        viewState.switchLoading(true)
        authService = NetworkService.authService
        compositeDisposable.add(
            authService.registerCourier(courier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        viewState.switchLoading(false)
                        viewState.onSuccessSignUp(it)
                    },
                    { error ->
                        viewState.switchLoading(false)
                        if (error is HttpException) {
                            val responseBody = error.response()?.errorBody()
                            viewState.showError(message = responseBody?.string().orEmpty())
                        } else {
                            viewState.showError(R.string.registration_error)
                        }
                    }
                )
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}