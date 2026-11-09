package com.example.clinetesushigo.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)

@Composable
fun SplashScreen(onStart: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // LOGOTIPO
        Box(
            modifier = Modifier
                .size(155.dp)
                .background(Beige, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🍣",
                fontSize = 85.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // NOMBRE
        Text(
            text = "SushiGo",
            fontSize = 46.sp,
            fontWeight = FontWeight.ExtraBold,
            color = WineRed
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "EL SABOR DE JAPÓN EN TUS MANOS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = SoftBrown,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Una experiencia deliciosa\nestá por comenzar.",
            fontSize = 19.sp,
            color = DarkText,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(55.dp))

        // BOTÓN COMENZAR
        Button(
            onClick = onStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = WineRed
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "COMENZAR",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "PREPARADO CON SABOR Y TRADICIÓN",
            fontSize = 10.sp,
            color = SoftBrown,
            letterSpacing = 1.sp
        )
    }
}