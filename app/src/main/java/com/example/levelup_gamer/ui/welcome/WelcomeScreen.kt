package com.example.levelup_gamer.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup_gamer.R
import com.example.levelup_gamer.ui.theme.ElectricBlue
import com.example.levelup_gamer.ui.theme.LightGray
import com.example.levelup_gamer.ui.theme.NeonGreen
import com.example.levelup_gamer.ui.theme.White
import com.example.levelup_gamer.ui.theme.orbitron
import com.example.levelup_gamer.ui.theme.roboto

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "BIENVENIDO a\nLEVELUP-GAMER",
                color = NeonGreen,
                fontSize = 24.sp,
                fontFamily = orbitron,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(180.dp)
            )
            Text(
                text = "DESAFIA TUS LIMITES",
                color = NeonGreen,
                fontSize = 20.sp,
                fontFamily = orbitron,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "¿Tienes una cuenta? Inicia sesión",
                color = LightGray,
                fontFamily = roboto,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { navController.navigate("login") },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("INICIAR SESION", color = White, fontFamily = roboto, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(8.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Regístrate para subir al nivel 1!",
                color = LightGray,
                fontFamily = roboto,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { navController.navigate("register") },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("REGISTRATE", color = White, fontFamily = roboto, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
