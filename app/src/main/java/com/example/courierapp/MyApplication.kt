package com.example.courierapp

import android.app.Application
import com.example.courierapp.di.AppComponent
import com.example.courierapp.di.AppModule
import com.example.courierapp.di.DaggerAppComponent


/**
 * Created by Andrey Morgunov on 15/03/2021.
 */

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(app: MyApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
}