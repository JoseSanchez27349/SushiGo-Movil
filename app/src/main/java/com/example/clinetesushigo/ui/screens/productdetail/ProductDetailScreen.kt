package com.example.clinetesushigo.ui.screens.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val White = Color(0xFFFFFFFF)

@Composable
fun ProductDetailScreen(
    name: String,
    description: String,
    price: Int,
    emoji: String,
    category: String,
    protein: String,
    vegetarian: Boolean,
    spicy: Boolean,
    preparation: String,
    imageUrl: String,
    onBack: () -> Unit,
    onAddToCart: (Int) -> Unit
) {

    var quantity by remember { mutableIntStateOf(1) }

    val total = price * quantity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
    ) {

        // FOTOGRAFÍA PRINCIPAL
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {

            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            TextButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = White
                )
            ) {
                Text(
                    text = "‹ Regresar",
                    color = WineRed,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            // CATEGORÍA
            Text(
                text = category.uppercase(),
                color = WineRed,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // NOMBRE
            Text(
                text = name,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(10.dp))

            // PRECIO
            Text(
                text = "$$price MXN",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = WineRed
            )

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalDivider(color = Color(0xFFE9E0D5))

            Spacer(modifier = Modifier.height(18.dp))

            // DESCRIPCIÓN
            Text(
                text = "Descripción",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 14.sp,
                color = SoftBrown,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // CARACTERÍSTICAS
            Text(
                text = "Características",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(12.dp))

            DetailTag("🍽️ $preparation")
            DetailTag("🥢 $protein")

            if (vegetarian) {
                DetailTag("🥑 Vegetariano")
            }

            if (spicy) {
                DetailTag("🌶️ Picante")
            } else {
                DetailTag("Sin picante")
            }

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(color = Color(0xFFE9E0D5))

            Spacer(modifier = Modifier.height(20.dp))

            // CANTIDAD
            Text(
                text = "Cantidad",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedButton(
                    onClick = {
                        if (quantity > 1) quantity--
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("-", fontSize = 20.sp, color = WineRed)
                }

                Text(
                    text = quantity.toString(),
                    modifier = Modifier.padding(horizontal = 22.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                OutlinedButton(
                    onClick = {
                        if (quantity < 20) quantity++
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("+", fontSize = 20.sp, color = WineRed)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // BOTÓN AGREGAR
            Button(
                onClick = {
                    onAddToCart(quantity)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WineRed
                )
            ) {

                Text(
                    text = "Agregar al carrito · $$total",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun DetailTag(text: String) {

    Surface(
        color = White,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp),
            color = DarkText,
            fontSize = 13.sp
        )
    }
}