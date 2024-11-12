package com.example.businessapp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class UserRepository(private val context: Context) {

    private val fileName = "profiles.json"

    // Сохранение нового профиля пользователя в файл
    fun saveProfile(profile: Profile) {
        val profiles = getProfiles().toMutableList()
        profiles.add(profile)
        saveProfilesToFile(profiles)
    }

    // Обновление профиля пользователя
    fun updateProfile(updatedProfile: Profile) {
        val profiles = getProfiles().toMutableList()
        val profileIndex = profiles.indexOfFirst { it.email == updatedProfile.email }
        if (profileIndex != -1) {
            profiles[profileIndex] = updatedProfile
            saveProfilesToFile(profiles)
        }
    }

    // Вспомогательный метод для записи профилей в файл
    private fun saveProfilesToFile(profiles: List<Profile>) {
        val jsonString = Gson().toJson(profiles)
        File(context.filesDir, fileName).writeText(jsonString)
    }

    // Получение списка всех профилей пользователей
    fun getProfiles(): List<Profile> {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) return emptyList()
        val jsonString = file.readText()
        val type = object : TypeToken<List<Profile>>() {}.type
        return Gson().fromJson(jsonString, type)
    }

    // Получение профиля пользователя по email
    fun getProfile(email: String): Profile? {
        return getProfiles().find { it.email == email }
    }

    // Проверка существования пользователя по email и паролю
    fun checkUser(email: String, passwordHash: String): Boolean {
        return getProfiles().any { it.email == email && it.passwordHash == passwordHash }
    }
}
