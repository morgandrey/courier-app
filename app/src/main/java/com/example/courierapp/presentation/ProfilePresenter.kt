package com.example.courierapp.presentation

import com.example.courierapp.R
import com.example.courierapp.models.Courier
import com.example.courierapp.remote.NetworkService
import com.example.courierapp.remote.ProfileService
import com.example.courierapp.views.ProfileView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.MvpPresenter
import retrofit2.HttpException


/**
 * Created by Andrey Morgunov on 12/03/2021.
 */

class ProfilePresenter : MvpPresenter<ProfileView>() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var profileService: ProfileService

    fun updateCourierData(courierId: Long, courier: Courier) {
        profileService = NetworkService.profileService
        compositeDisposable.add(
            profileService.updateCourierProfile(courierId, courier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { viewState.onSuccessUpdateData() },
                    { error ->
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

    fun getCourier(courierId: Long) {
        viewState.switchLoading(true)
        profileService = NetworkService.profileService
        compositeDisposable.add(
            profileService.getCourier(courierId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { courier ->
                        viewState.switchLoading(false)
                        viewState.onSuccessGetCourier(courier)
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