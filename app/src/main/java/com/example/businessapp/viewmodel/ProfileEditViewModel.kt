package com.example.businessapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.businessapp.data.Profile
import com.example.businessapp.data.UserRepository

class ProfileEditViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun updateProfile(email: String, bio: String?, profileImageUrl: String?) {
        val profile = userRepository.getProfile(email)
        profile?.let {
            it.bio = bio
            it.profileImageUrl = profileImageUrl
            userRepository.updateProfile(it)
        }
    }
}
