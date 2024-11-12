package com.example.businessapp.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import com.example.businessapp.viewmodel.ProfileViewModel
import android.R.drawable.ic_input_add
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.businessapp.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    email: String,
    onProfileImageChange: (Uri) -> Unit,
    onSaveChanges: (String) -> Unit,
    onLogoutClick: () -> Unit
) {
    // Загружаем профиль при запуске экрана
    LaunchedEffect(email) {
        viewModel.loadProfile(email)
    }

    // Наблюдаем за состоянием профиля
    val profile by viewModel.profile.collectAsState()

    // Локальные переменные для редактирования
    var editedBio by remember { mutableStateOf(profile?.bio ?: "") }
    var showSaveButton by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onProfileImageChange(it) // Передаем выбранный Uri в ViewModel
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Изображение профиля с иконкой "+"
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.Gray, CircleShape)
                .clickable {
                    imagePickerLauncher.launch("image/*")
                },
            contentAlignment = Alignment.BottomEnd
        ) {
            if (profile?.profileImageUrl != null) {
                AsyncImage(
                    model = profile?.profileImageUrl,
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = "+",
                    fontSize = 40.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "Add Profile Image",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Blue, CircleShape)
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Text(text = profile?.firstName ?: "User Name", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        //Text(text = profile?.jobTitle ?: "Job Title", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        // Редактируемый bio
        TextField(
            value = editedBio,
            onValueChange = {
                editedBio = it
                showSaveButton = editedBio != profile?.bio
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(

                focusedIndicatorColor = Color.Transparent, // Убираем подчеркивание при фокусе
                unfocusedIndicatorColor = Color.Transparent // Убираем подчеркивание при отсутствии фокуса
            ),
            shape = RoundedCornerShape(16.dp),
            label = { Text("Bio") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Save"
        if (showSaveButton) {
            Button(onClick = {
                onSaveChanges(editedBio)
                showSaveButton = false
            }) {
                Text("Save")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Logout"
        Button(onClick = onLogoutClick, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
            Text("Logout", color = Color.White)
        }
    }
}