package com.example.courierapp.util

import android.content.Context
import android.content.SharedPreferences
import com.example.courierapp.models.Courier
import com.google.gson.Gson


/**
 * Created by Andrey Morgunov on 12/03/2021.
 */

class PreferencesManager(context: Context) {

    companion object {
        const val FILE_NAME = "preferences"
        const val PREF_PIN = "pin"
        const val PREF_COURIER = "courier"
    }

    private var preferences: SharedPreferences? = null

    init {
        preferences = context.getSharedPreferences(FILE_NAME, 0)
    }

    private fun getEditor(): SharedPreferences.Editor {
        return preferences!!.edit()
    }

    fun setPin(data: String) {
        getEditor().putString(PREF_PIN, data).apply()
    }

    fun getPin(): String? {
        return preferences!!.getString(PREF_PIN, "")
    }

    fun setCourier(courier: Courier) {
        val gson = Gson()
        val json = gson.toJson(courier)
        getEditor().putString(PREF_COURIER, json).apply()
    }

    fun getCourier(): Courier? {
        val gson = Gson()
        return gson.fromJson(preferences!!.getString(PREF_COURIER, null), Courier::class.java)
    }

    fun deleteAllPreferences() {
        getEditor().remove(PREF_PIN).apply()
        getEditor().remove(PREF_COURIER).apply()
    }
}