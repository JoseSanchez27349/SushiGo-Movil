package com.example.clinetesushigo.ui.screens.promotions

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
    val imageUrl: String,
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
            name = "Combo SushiGo",
            description = "Dos rollos de sushi y una bebida.",
            emoji = "🍣",
            imageUrl = "https://images.unsplash.com/photo-1579871494447-9811cf80d66c",
            originalPrice = 235,
            offerPrice = 199
        ),

        Promotion(
            name = "Pareja japonesa",
            description = "Dos rollos especiales y dos bebidas.",
            emoji = "🍱",
            imageUrl = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351",
            originalPrice = 290,
            offerPrice = 249
        ),

        Promotion(
            name = "Combo oriental",
            description = "Yakimeshi acompañado de gyozas.",
            emoji = "🥟",
            imageUrl = "https://images.unsplash.com/photo-1563245372-f21724e3856d",
            originalPrice = 160,
            offerPrice = 129
        ),

        Promotion(
            name = "Combo familiar",
            description = "Cuatro rollos de sushi y cuatro bebidas.",
            emoji = "🍣",
            imageUrl = "https://images.unsplash.com/photo-1553621042-f6e147245754",
            originalPrice = 480,
            offerPrice = 399
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 25.dp)
    ) {

        // ESPACIO DESPUÉS DEL ENCABEZADO GENERAL

        Spacer(modifier = Modifier.height(25.dp))

        // TÍTULO PRINCIPAL

        Column(
            modifier = Modifier.padding(horizontal = 22.dp)
        ) {

            Text(
                text = "SUSHIGO EXCLUSIVO",
                color = WineRed,
                fontSize = 11.sp,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = "Ofertas especiales",
                color = DarkText,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = "Disfruta tus platillos favoritos con precios especiales.",
                color = SoftBrown,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // CARRUSEL DE PROMOCIONES

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Descubre nuestros combos",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "${promotions.size} ofertas",
                fontSize = 11.sp,
                color = WineRed,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(promotions) { promotion ->

                Card(
                    modifier = Modifier
                        .width(285.dp)
                        .height(205.dp),
                    shape = RoundedCornerShape(19.dp),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        AsyncImage(
                            model = promotion.imageUrl,
                            contentDescription = promotion.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.85f)
                                        )
                                    )
                                )
                        )

                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(13.dp),
                            color = WineRed,
                            shape = RoundedCornerShape(7.dp)
                        ) {

                            Text(
                                text = "OFERTA ESPECIAL",
                                modifier = Modifier.padding(
                                    horizontal = 9.dp,
                                    vertical = 6.dp
                                ),
                                color = White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {

                            Text(
                                text = promotion.name,
                                color = White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = promotion.description,
                                color = White,
                                fontSize = 11.sp
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = "$${promotion.offerPrice} MXN",
                                color = White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // COMBOS DISPONIBLES

        Column(
            modifier = Modifier.padding(horizontal = 22.dp)
        ) {

            Text(
                text = "Combos disponibles",
                color = DarkText,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Elige tu favorito y disfruta algo delicioso.",
                color = SoftBrown,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(17.dp))

        // TARJETAS DE PROMOCIONES

        promotions.forEach { promotion ->

            val discount =
                (promotion.originalPrice - promotion.offerPrice) *
                        100 / promotion.originalPrice

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(bottom = 17.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {

                Column(
                    modifier = Modifier.padding(15.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AsyncImage(
                            model = promotion.imageUrl,
                            contentDescription = promotion.name,
                            modifier = Modifier
                                .size(105.dp)
                                .background(
                                    Cream,
                                    RoundedCornerShape(14.dp)
                                ),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(13.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Surface(
                                color = WineRed.copy(alpha = 0.10f),
                                shape = RoundedCornerShape(7.dp)
                            ) {

                                Text(
                                    text = "$discount% DE DESCUENTO",
                                    color = WineRed,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(
                                        horizontal = 8.dp,
                                        vertical = 6.dp
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = promotion.name,
                                color = DarkText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

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
                                textDecoration = TextDecoration.LineThrough
                            )

                            Text(
                                text = "$${promotion.offerPrice} MXN",
                                color = WineRed,
                                fontSize = 22.sp,
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

        // PIE DE PÁGINA

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 22.dp),
            color = Beige
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "SUSHIGO • SABOR Y TRADICIÓN",
            modifier = Modifier.fillMaxWidth(),
            color = SoftBrown,
            fontSize = 11.sp,
            letterSpacing = 2.sp
        )
    }
}