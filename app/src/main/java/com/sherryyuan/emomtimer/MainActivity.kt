package com.sherryyuan.emomtimer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        val appBarLayout: AppBarLayout = findViewById(R.id.app_bar_layout)
        val bottomNavView: BottomNavigationView = findViewById(R.id.nav_view)

        ViewCompat.setOnApplyWindowInsetsListener(bottomNavView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = insets.bottom)
            windowInsets
        }

        val navController = findNavController(R.id.nav_host_fragment)
        // Hide toolbar and bottom navigation when timer is counting down.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_timer_countdown
                || destination.id == R.id.navigation_workout_complete
            ) {
                appBarLayout.isVisible = false
                bottomNavView.isVisible = false
            } else {
                appBarLayout.isVisible = true
                bottomNavView.isVisible = true
            }
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_workouts_tab,
                // TODO: update to timer selection once tabata support is finished
                R.id.navigation_emom_timer_config, // R.id.navigation_timer_selection,
                R.id.navigation_settings_tab
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
