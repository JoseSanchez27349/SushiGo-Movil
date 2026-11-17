package com.example.clinetesushigo.ui.screens.branches

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun BranchesScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(22.dp)
    ) {

        Text(
            text = "Nuestras sucursales",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Encuentra tu SushiGo más cercano.",
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
                    text = "📍",
                    fontSize = 35.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Sucursal principal",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = WineRed
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Próximamente encontrarás aquí " +
                            "la dirección y los horarios de nuestra sucursal.",
                    fontSize = 13.sp,
                    color = SoftBrown,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Estamos preparando más ubicaciones para ti.",
            color = SoftBrown,
            fontSize = 13.sp
        )
    }
}