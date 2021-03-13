package com.example.courierapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.courierapp.databinding.ActivityMainBinding
import com.example.courierapp.util.PreferencesManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CourierApp)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(applicationContext)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_SELECTED
        binding.bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        setSupportActionBar(binding.toolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.profileFragment,
                R.id.historyFragment,
                R.id.settingsFragment,
                R.id.pinLockFragment,
                R.id.signInFragment
            )
        )
        binding.bottomNav.menu.getItem(1).isChecked = true
        setupActionBarWithNavController(navController, appBarConfiguration)
        if (isCourierLoggedIn()) {
            navController.navigate(R.id.pinLockFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.pinLockFragment ||
                destination.id == R.id.signInFragment ||
                destination.id == R.id.registerFragment
            ) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    private fun isCourierLoggedIn(): Boolean {
        val courier = preferencesManager.getCourier()
        return courier != null
    }


    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_profile -> {
                    navController.navigate(R.id.profileFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_history -> {
                    navController.navigate(R.id.historyFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    navController.navigate(R.id.settingsFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}