package com.example.courierapp.di

import android.content.Context
import com.example.courierapp.util.PreferencesManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Andrey Morgunov on 15/03/2021.
 */

@Module
class PreferencesModule {
    @Provides
    @Singleton
    fun providePreferences(context: Context) = PreferencesManager(context)
}