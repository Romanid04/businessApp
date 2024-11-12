package com.example.businessapp.data

import com.example.businessapp.R

data class OnBoardingPage(
    val imageId: Int,
    val title: String
)

val onboardingPages = listOf(
    OnBoardingPage(
        imageId = R.drawable.wfh_1,
        title = "Узнавай\nо премьерах",
    ),
    OnBoardingPage(
        imageId = R.drawable.wfh_2,
        title = "Создавай\nколлекции",
    ),
    OnBoardingPage(
        imageId = R.drawable.wfh_3,
        title = "Делись\nс друзьями",
    )
)