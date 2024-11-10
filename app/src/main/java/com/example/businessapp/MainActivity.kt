package com.example.businessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.businessapp.data.UserRepository
import com.example.businessapp.navigation.AppNavHost
import com.example.businessapp.presentation.ProfileScreen
import com.example.businessapp.ui.theme.BusinessAppTheme
import com.example.businessapp.viewmodel.ProfileViewModel
import com.example.businessapp.viewmodel.ProfileViewModelFactory
import com.example.businessapp.viewmodel.RegistrationViewModel
import com.example.businessapp.viewmodel.RegistrationViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userRepository = UserRepository(applicationContext)

        val profileViewModel = ViewModelProvider(this, ProfileViewModelFactory(userRepository))
            .get(ProfileViewModel::class.java)

        val registrationViewModel = ViewModelProvider(
            this,
            RegistrationViewModelFactory(userRepository)
        )[RegistrationViewModel::class.java]

        val email = "wer"
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            AppNavHost(
                navController = navController,
                registrationViewModel = registrationViewModel,
                profileViewModel = profileViewModel,
                userRepository = userRepository
            )
            /*
            BusinessAppTheme {
                ProfileScreen(
                    viewModel = profileViewModel,
                    email = email,
                    onEditClick = {
                        // Navigate to ProfileEditScreen or show edit UI for bio/image
                    },
                    onLogoutClick = {
                        // Handle logout logic (e.g., navigate back to login screen)
                    }
                )

             */
                /*
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    registrationViewModel = registrationViewModel,
                    userRepository = userRepository
                )*/
            }
        }
    }

