package com.example.courierapp.di

import com.example.courierapp.ui.*
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Andrey Morgunov on 15/03/2021.
 */

@Singleton
@Component(modules = [PreferencesModule::class, AppModule::class])
interface AppComponent {
    fun inject(target: AvailableOrderListFragment)
    fun inject(target: ActiveOrderListFragment)
    fun inject(target: AvailableOrderDetailsFragment)
    fun inject(target: ActiveOrderDetailsFragment)
    fun inject(target: ProfileFragment)
}