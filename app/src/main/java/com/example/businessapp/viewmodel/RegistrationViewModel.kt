package com.example.businessapp.viewmodel


import androidx.lifecycle.ViewModel
import com.example.businessapp.data.Profile
import com.example.businessapp.data.UserRepository


class RegistrationViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun registerUser(firstName: String, lastName: String, email: String, country: String, password: String) {
        val profile = Profile(
            firstName = firstName,
            lastName = lastName,
            email = email,
            country = country,
            passwordHash = password // Используйте хеширование в реальном проекте
        )
        userRepository.saveProfile(profile)
    }
}
