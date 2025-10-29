package com.example.levelup_gamer.ui.register

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.levelup_gamer.R
import com.example.levelup_gamer.ui.theme.Black
import com.example.levelup_gamer.ui.theme.ElectricBlue
import com.example.levelup_gamer.ui.theme.LightGray
import com.example.levelup_gamer.ui.theme.NeonGreen
import com.example.levelup_gamer.ui.theme.White
import com.example.levelup_gamer.ui.theme.orbitron
import java.time.Instant
import java.time.Year
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = viewModel()
) {
    val uiState by registerViewModel.uiState.collectAsState()

    Surface(color = Black, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!uiState.registrationCompleted) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        if (uiState.currentStep > 1) {
                            registerViewModel.previousStep()
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "Back",
                            tint = NeonGreen
                        )
                    }
                    Text(
                        text = "REGISTRO",
                        fontFamily = orbitron,
                        color = NeonGreen,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(48.dp)) // Spacer to balance the IconButton
                }
                Spacer(modifier = Modifier.height(32.dp))
                StepIndicator(currentStep = uiState.currentStep)
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.registrationCompleted) {
                RegistrationCompletedContent(navController = navController)
            } else {
                when (uiState.currentStep) {
                    1 -> Step1Content(registerViewModel, uiState)
                    2 -> Step2Content(registerViewModel, uiState)
                    3 -> Step3Content(registerViewModel, uiState)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (uiState.currentStep < 3 && !uiState.registrationCompleted) {
                Button(
                    onClick = { registerViewModel.nextStep() },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text("Siguiente", fontSize = 18.sp, color = Black)
                    Icon(Icons.Default.ArrowForward, contentDescription = "Siguiente", tint = Black)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RegistrationCompletedContent(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Usuario registrado correctamente.\nPresione siguiente para continuar.",
            color = White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate("home") { popUpTo("welcome") } },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text("Siguiente", fontSize = 18.sp, color = Black)
            Icon(Icons.Default.ArrowForward, contentDescription = "Siguiente", tint = Black)
        }
    }
}

@Composable
fun StepIndicator(currentStep: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        val steps = 3
        for (step in 1..steps) {
            Step(number = step, isActive = step <= currentStep)
            if (step < steps) {
                Line(isActive = step < currentStep)
            }
        }
    }
}

@Composable
fun Step(number: Int, isActive: Boolean) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(32.dp)
            .background(
                color = if (isActive) ElectricBlue else LightGray,
                shape = CircleShape
            )
    ) {
        Text(text = number.toString(), color = White)
    }
}

@Composable
fun Line(isActive: Boolean) {
    Canvas(modifier = Modifier.size(width = 40.dp, height = 2.dp)) {
        drawLine(
            brush = SolidColor(if (isActive) NeonGreen else LightGray),
            start = Offset(0f, center.y),
            end = Offset(size.width, center.y),
            strokeWidth = 2.dp.toPx()
        )
    }
}

@Composable
fun Step1Content(viewModel: RegisterViewModel, uiState: RegistrationUiState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Correo Electronico") },
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White,
                unfocusedBorderColor = White,
                focusedLabelColor = White,
                unfocusedLabelColor = White,
                cursorColor = White,
                focusedTextColor = White,
                unfocusedTextColor = White
            ),
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Text(
            text = "Regístrate con el correo de Duoc y obtén un 20% de descuento en todos nuestros juegos de por vida",
            color = White,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White,
                unfocusedBorderColor = White,
                focusedLabelColor = White,
                unfocusedLabelColor = White,
                cursorColor = White,
                focusedTextColor = White,
                unfocusedTextColor = White
            ),
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White,
                unfocusedBorderColor = White,
                focusedLabelColor = White,
                unfocusedLabelColor = White,
                cursorColor = White,
                focusedTextColor = White,
                unfocusedTextColor = White
            ),
            modifier = Modifier.fillMaxWidth(0.9f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step2Content(viewModel: RegisterViewModel, uiState: RegistrationUiState) {
    var showDatePicker by remember { mutableStateOf(false) }
    val currentYear = Year.now().value
    val datePickerState = rememberDatePickerState(yearRange = 1900..currentYear)

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
                            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                            viewModel.onBirthDateChange(selectedDate.format(formatter))
                        }
                        showDatePicker = false
                    }
                ) { Text("Aceptar", color = NeonGreen) }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) { Text("Cancelar", color = NeonGreen) }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Nombres") },
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White,
                unfocusedBorderColor = White,
                focusedLabelColor = White,
                unfocusedLabelColor = White,
                cursorColor = White,
                focusedTextColor = White,
                unfocusedTextColor = White
            ),
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = { viewModel.onLastNameChange(it) },
            label = { Text("Apellidos") },
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White,
                unfocusedBorderColor = White,
                focusedLabelColor = White,
                unfocusedLabelColor = White,
                cursorColor = White,
                focusedTextColor = White,
                unfocusedTextColor = White
            ),
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Box(modifier = Modifier.fillMaxWidth(0.9f)) {
            OutlinedTextField(
                value = uiState.birthDate,
                onValueChange = {},
                label = { Text("Fecha de Nacimiento") },
                readOnly = true,
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = White,
                    unfocusedBorderColor = White,
                    focusedLabelColor = White,
                    unfocusedLabelColor = White,
                    cursorColor = White,
                    focusedTextColor = White,
                    unfocusedTextColor = White,
                    disabledTextColor = White,
                    disabledBorderColor = White,
                    disabledLabelColor = White
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { showDatePicker = true }
            )
        }

        OutlinedTextField(
            value = uiState.username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = { Text("Nombre de Usuario") },
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = White,
                unfocusedBorderColor = White,
                focusedLabelColor = White,
                unfocusedLabelColor = White,
                cursorColor = White,
                focusedTextColor = White,
                unfocusedTextColor = White
            ),
            modifier = Modifier.fillMaxWidth(0.9f)
        )
    }
}

@Composable
fun Step3Content(viewModel: RegisterViewModel, uiState: RegistrationUiState) {
    val imagePicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        viewModel.onProfileImageChange(uri)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (uiState.profileImageUri != null) {
            AsyncImage(
                model = uiState.profileImageUri,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_placeholder),
                contentDescription = "Profile Placeholder",
                modifier = Modifier.size(150.dp),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(NeonGreen)
            )
        }

        TextButton(onClick = { /* TODO: Launch camera */ }) {
            Text("Tomar Foto de Perfil", color = White)
        }
        TextButton(onClick = { imagePicker.launch("image/*") }) {
            Text("Elegir de la galeria", color = White)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = uiState.termsAccepted,
                onCheckedChange = { viewModel.onTermsAcceptedChange(it) }
            )
            Text("Términos y Condiciones del servicio", color = White)
        }
        Button(
            onClick = { viewModel.completeRegistration() },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text("Registrar", fontSize = 18.sp, color = White)
        }
        Button(
            onClick = { viewModel.completeRegistration() },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text("Omitir", fontSize = 18.sp, color = White)
        }
    }
}
