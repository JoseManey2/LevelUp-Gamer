package com.example.levelup_gamer.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup_gamer.R
import com.example.levelup_gamer.ui.theme.Black
import com.example.levelup_gamer.ui.theme.ElectricBlue
import com.example.levelup_gamer.ui.theme.NeonGreen
import com.example.levelup_gamer.ui.theme.White
import com.example.levelup_gamer.ui.theme.orbitron

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(color = Black, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "INICIAR SESIÓN",
                fontFamily = orbitron,
                color = NeonGreen,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Level-Up Gamer Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
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
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
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
                )
            )
            TextButton(onClick = { /* TODO */ }) {
                Text("Olvidé mi contraseña", color = White, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("home") },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                modifier = Modifier.size(width = 280.dp, height = 50.dp)
            ) {
                Text("Iniciar Sesión", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(32.dp))
            TextButton(onClick = { navController.navigate("register") }) {
                Text(
                    text = "¿No tienes cuenta? Regístrate\ny sube al nivel 1",
                    color = ElectricBlue,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
