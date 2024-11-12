package com.example.businessapp.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.businessapp.data.Profile
import androidx.lifecycle.viewModelScope
import com.example.businessapp.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    // StateFlow для хранения профиля
    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile

    // Метод для загрузки профиля на основе email
    fun loadProfile(email: String) {
        viewModelScope.launch {
            _profile.value = userRepository.getProfile(email)
        }
    }

    // Метод для обновления bio
    fun updateProfileBio(email: String, newBio: String) {
        viewModelScope.launch {
            val profile = userRepository.getProfile(email)
            profile?.let {
                it.bio = newBio
                userRepository.updateProfile(it)
                _profile.value = it // Обновляем состояние, чтобы UI перерисовался
            }
        }
    }

    // Метод для обновления изображения профиля
    fun updateProfileImage(email: String, newImageUri: Uri) {
        viewModelScope.launch {
            val profile = userRepository.getProfile(email)
            profile?.let {
                it.profileImageUrl = newImageUri.toString()
                userRepository.updateProfile(it)
                _profile.value = it // Обновляем состояние профиля
            }
        }
    }
}