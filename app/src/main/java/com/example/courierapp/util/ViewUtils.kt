package com.example.courierapp.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showToast(context: Context, message: Int) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun hideApp(activity: FragmentActivity, viewLifecycleOwner: LifecycleOwner) {
    activity.onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(activity, intent, null)
            }
        })
}