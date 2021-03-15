package com.example.courierapp.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import java.util.*


/**
 * Created by Andrey Morgunov on 15/03/2021.
 */

object LocalHelper {

    fun setLocale(activity: Activity, language: String) {
        updateResourcesLegacy(activity, language)
    }

    //TODO Update language doesn't work
    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(activity: Activity, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration: Configuration = activity.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
    }

    @Suppress("DEPRECATION")
    fun updateResourcesLegacy(activity: Activity, language: String) {
        val res = activity.resources
        val conf = res.configuration
        conf.setLocale(Locale(language.toLowerCase(Locale.ROOT)))
        res.updateConfiguration(conf, null)
    }
}