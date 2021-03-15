package com.example.courierapp.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.courierapp.MainActivity
import com.example.courierapp.R
import com.example.courierapp.util.LocalHelper

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == "language_reply") {
                    val lang = sharedPreferences.getString(key, "")
                    val editor = sharedPreferences.edit()
                    editor.putString(key, lang)
                    editor.apply()
                    LocalHelper.setLocale(requireActivity(), lang!!)
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        sp.registerOnSharedPreferenceChangeListener(listener)
    }
}