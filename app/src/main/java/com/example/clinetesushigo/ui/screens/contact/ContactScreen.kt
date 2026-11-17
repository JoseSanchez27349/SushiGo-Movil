package com.example.clinetesushigo.ui.screens.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val White = Color.White

@Composable
fun ContactScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(22.dp)
    ) {

        Text(
            text = "Contáctanos",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Estamos aquí para atenderte.",
            fontSize = 14.sp,
            color = SoftBrown
        )

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "📞 Atención al cliente",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = WineRed
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Próximamente podrás comunicarte " +
                            "con nuestro equipo de atención.",
                    fontSize = 14.sp,
                    color = SoftBrown,
                    lineHeight = 21.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "✉️ Correo electrónico",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = WineRed
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Canal de atención SushiGo",
                    fontSize = 14.sp,
                    color = SoftBrown
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "SUSHIGO • SABOR Y TRADICIÓN",
            fontSize = 11.sp,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold,
            color = SoftBrown
        )
    }
}