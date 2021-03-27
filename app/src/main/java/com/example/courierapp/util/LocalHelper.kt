package com.example.courierapp.util

import android.app.Activity
import java.util.*


/**
 * Created by Andrey Morgunov on 15/03/2021.
 */

object LocalHelper {
    @Suppress("DEPRECATION")
    fun setLocale(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = activity.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}