package com.example.businessapp.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.businessapp.data.UserRepository
import com.example.businessapp.presentation.LoginScreen
import com.example.businessapp.presentation.ProfileScreen
import com.example.businessapp.presentation.RegistrationScreen
import com.example.businessapp.viewmodel.ProfileViewModel
import com.example.businessapp.viewmodel.RegistrationViewModel

sealed class Screen(val route: String) {
    object Registration : Screen("registration")
    object Login : Screen("login")
    object Profile : Screen("profile")
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    registrationViewModel: RegistrationViewModel,
    profileViewModel: ProfileViewModel,
    userRepository: UserRepository
) {
    NavHost(navController = navController, startDestination = Screen.Registration.route) {

        // Экран регистрации
        composable(Screen.Registration.route) {
            RegistrationScreen(
                viewModel = registrationViewModel,
                onNextClick = { navController.navigate(Screen.Login.route) }
            )
        }

        // Экран входа
        composable(Screen.Login.route) {
            LoginScreen(
                userRepository = userRepository,
                onLoginSuccess = { email ->
                    navController.navigate("${Screen.Profile.route}/$email") {
                        // Удаляем экран входа из стека, чтобы при нажатии назад не возвращаться на него
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() } // Переход назад на предыдущий экран
            )
        }

        // Экран профиля
        composable("${Screen.Profile.route}/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: return@composable
            ProfileScreen(
                viewModel = profileViewModel,
                email = email,
                onProfileImageChange = { newImageUri ->
                    profileViewModel.updateProfileImage(email, newImageUri)
                },
                onSaveChanges = { newBio ->
                    profileViewModel.updateProfileBio(email, newBio)
                },
                onLogoutClick = {
                    navController.navigate(Screen.Login.route) {
                        // Очищаем стек до экрана входа, чтобы избежать перехода назад на экран профиля
                        popUpTo(Screen.Profile.route) { inclusive = true }
                    }
                }
            )
        }
    }
}