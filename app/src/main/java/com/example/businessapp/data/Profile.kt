package com.example.businessapp.data


data class Profile(
    val firstName: String,
    val lastName: String,
    val email: String,
    val country: String,
    val passwordHash: String,
    var bio: String? = null,                  // Биография может быть null
    var profileImageUrl: String? = null       // Ссылка на изображение профиля может быть null
)
