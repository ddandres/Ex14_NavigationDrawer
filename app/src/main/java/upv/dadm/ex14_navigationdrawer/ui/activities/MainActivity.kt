/*
 * Copyright (c) 2022-2024 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */
package upv.dadm.ex14_navigationdrawer.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import upv.dadm.ex14_navigationdrawer.R
import upv.dadm.ex14_navigationdrawer.databinding.ActivityMainBinding

/**
 * Displays a Navigation Drawer that allows the navigation between six Fragments
 * (they just show a message).
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        val binding = ActivityMainBinding.inflate(layoutInflater)
        // Enable edge-to-edge display
        enableEdgeToEdge()
        // Set the activity content to the root element of the generated view
        setContentView(binding.root)
        // Prevent the layout from overlapping with system bars in edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(binding.top) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Make the custom ToolBar the ActionBar
        setSupportActionBar(binding.toolbar)
        // Get an instance of the NavController.
        // findNavController() does not work properly with FragmentContainerView in onCreate()
        val navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        // The AppBarConfiguration sets as starting fragments all those provided in the set
        // (shows a drawer icon instead of the Up navigation icon), and automatically opens
        // the drawer when the icon is clicked
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.firstFragment,
                R.id.secondFragment,
                R.id.thirdFragment,
                R.id.fourthFragment,
                R.id.fifthFragment,
                R.id.sixthFragment
            ), binding.drawerLayout
        )
        // Configure the ActionBar to work with the NavController,
        // so that its title is updated when navigating
        setupActionBarWithNavController(navController, appBarConfiguration)
        // Configure the NavigationView to work with the NavController,
        // so that it automatically navigates to the Fragment associated to the selected item
        binding.navigationView.setupWithNavController(navController)
    }

    /**
     * Manages the Up navigation.
     * First it tries to navigate Up in the navigation hierarchy from the NavController and,
     * if it does not succeed, then tries again from the AppCompatActivity.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(
            findNavController(R.id.navHostFragment),
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }
}