package com.example.clinetesushigo.ui.screens.promotions

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinetesushigo.data.repository.CartManager

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color.White

private data class Promotion(
    val name: String,
    val description: String,
    val emoji: String,
    val originalPrice: Int,
    val offerPrice: Int
)

@Composable
fun PromotionsScreen(
    onBack: () -> Unit,
    onOpenCart: () -> Unit
) {
    val context = LocalContext.current

    val promotions = listOf(
        Promotion(
            "Combo SushiGo",
            "Dos rollos de sushi y una bebida.",
            "🍣",
            235,
            199
        ),
        Promotion(
            "Pareja japonesa",
            "Dos rollos especiales y dos bebidas.",
            "🍱",
            290,
            249
        ),
        Promotion(
            "Combo oriental",
            "Yakimeshi acompañado de gyozas.",
            "🥟",
            160,
            129
        ),
        Promotion(
            "Combo familiar",
            "Cuatro rollos de sushi y cuatro bebidas.",
            "🍣",
            480,
            399
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(22.dp)
    ) {

        // ENCABEZADO
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text(
                    text = "← Regresar",
                    color = WineRed,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = onOpenCart) {
                Text("🛒 Carrito", color = WineRed)
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "PROMOCIONES",
            color = WineRed,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Ofertas especiales",
            color = DarkText,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Disfruta tus platillos favoritos a un precio especial.",
            color = SoftBrown,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        // BANNER PRINCIPAL
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = WineRed
            )
        ) {
            Column(
                modifier = Modifier.padding(22.dp)
            ) {
                Text(
                    text = "SUSHIGO PRESENTA",
                    color = White.copy(alpha = 0.8f),
                    fontSize = 11.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "¡El sabor japonés\ncon mejores precios!",
                    color = White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Aprovecha nuestras promociones.",
                    color = White.copy(alpha = 0.9f),
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Ofertas disponibles",
            color = DarkText,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Elige tu combo favorito y agrégalo al carrito.",
            color = SoftBrown,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // TARJETAS DE PROMOCIONES
        promotions.forEach { promotion ->

            val discount = (
                    (promotion.originalPrice - promotion.offerPrice) *
                            100 / promotion.originalPrice
                    )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(17.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(75.dp),
                            shape = RoundedCornerShape(15.dp),
                            color = Cream
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = promotion.emoji,
                                    fontSize = 42.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                color = WineRed.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(7.dp)
                            ) {
                                Text(
                                    text = "$discount% DE DESCUENTO",
                                    color = WineRed,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(
                                        horizontal = 8.dp,
                                        vertical = 5.dp
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(7.dp))

                            Text(
                                text = promotion.name,
                                color = DarkText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = promotion.description,
                                color = SoftBrown,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    HorizontalDivider(color = Beige)

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "$${promotion.originalPrice} MXN",
                                color = SoftBrown,
                                fontSize = 12.sp,
                                style = androidx.compose.ui.text.TextStyle(
                                    textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                                )
                            )

                            Text(
                                text = "$${promotion.offerPrice} MXN",
                                color = WineRed,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        Button(
                            onClick = {
                                CartManager.addProduct(
                                    name = promotion.name,
                                    description = promotion.description,
                                    price = promotion.offerPrice,
                                    emoji = promotion.emoji
                                )

                                Toast.makeText(
                                    context,
                                    "${promotion.name} agregado al carrito",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WineRed
                            ),
                            shape = RoundedCornerShape(11.dp)
                        ) {
                            Text(
                                text = "AGREGAR +",
                                color = White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        HorizontalDivider(color = Beige)

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "SUSHIGO • SABOR Y TRADICIÓN",
            modifier = Modifier.fillMaxWidth(),
            color = SoftBrown,
            fontSize = 11.sp,
            letterSpacing = 2.sp
        )
    }
}