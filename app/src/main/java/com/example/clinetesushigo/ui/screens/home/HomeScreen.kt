package com.example.clinetesushigo.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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

data class Product(
    val name: String,
    val description: String,
    val price: String,
    val emoji: String
)

data class Offer(
    val title: String,
    val description: String,
    val imageUrl: String,
    val label: String
)

@Composable
fun HomeScreen(
    onOpenCart: () -> Unit,
    onOpenMenu: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenPromotions: () -> Unit
) {

    val context = LocalContext.current

    var search by remember { mutableStateOf("") }

    val offers = listOf(
        Offer(
            title = "Sushi para compartir",
            description = "Disfruta tus rollos favoritos.",
            imageUrl = "https://images.unsplash.com/photo-1579871494447-9811cf80d66c",
            label = "FAVORITOS"
        ),
        Offer(
            title = "Una experiencia japonesa",
            description = "Descubre sabores únicos.",
            imageUrl = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351",
            label = "RECOMENDACIÓN"
        ),
        Offer(
            title = "Momentos deliciosos",
            description = "El sabor perfecto para hoy.",
            imageUrl = "https://images.unsplash.com/photo-1553621042-f6e147245754",
            label = "ESPECIAL"
        )
    )

    val categories = listOf(
        "🍣 Sushi",
        "🍜 Ramen",
        "🍚 Yakimeshi",
        "🥟 Entradas",
        "🥤 Bebidas",
        "🍰 Postres"
    )

    val products = listOf(
        Product(
            "California Roll",
            "Rollo de sushi con surimi y aguacate",
            "$95",
            "🍣"
        ),
        Product(
            "Philadelphia Roll",
            "Salmón, queso crema y aguacate",
            "$115",
            "🍱"
        ),
        Product(
            "Yakimeshi",
            "Arroz frito con verduras y huevo",
            "$85",
            "🍚"
        ),
        Product(
            "Gyozas",
            "Empanaditas japonesas",
            "$75",
            "🥟"
        )
    )

    val filteredProducts = products.filter {
        it.name.contains(search, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 25.dp)
    ) {

        // ENCABEZADO

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(top = 25.dp)
        ) {

            Text(
                text = "¡Hola! 👋",
                fontSize = 14.sp,
                color = SoftBrown
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "¿Qué se te antoja hoy?",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Descubre el auténtico sabor de Japón.",
                fontSize = 13.sp,
                color = SoftBrown
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // BUSCADOR

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            placeholder = {
                Text(
                    "Buscar sushi, bebidas, entradas...",
                    fontSize = 13.sp,
                    color = SoftBrown
                )
            },
            leadingIcon = {
                Text(
                    "⌕",
                    fontSize = 25.sp,
                    color = SoftBrown
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = White,
                focusedContainerColor = White,
                unfocusedBorderColor = Beige,
                focusedBorderColor = WineRed,
                focusedTextColor = DarkText,
                unfocusedTextColor = DarkText,
                cursorColor = WineRed
            )
        )

        Spacer(modifier = Modifier.height(28.dp))

        // CARRUSEL DE OFERTAS

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = "Ofertas especiales",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "Algo delicioso te está esperando",
                    fontSize = 12.sp,
                    color = SoftBrown
                )
            }

            Text(
                text = "Ver todas",
                color = WineRed,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onOpenPromotions()
                }
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(offers) { offer ->

                Card(
                    modifier = Modifier
                        .width(285.dp)
                        .height(185.dp)
                        .clickable {
                            onOpenPromotions()
                        },
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        AsyncImage(
                            model = offer.imageUrl,
                            contentDescription = offer.title,
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
                                            Color.Black.copy(alpha = 0.82f)
                                        )
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(17.dp)
                        ) {

                            Surface(
                                color = WineRed,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = offer.label,
                                    modifier = Modifier.padding(
                                        horizontal = 9.dp,
                                        vertical = 5.dp
                                    ),
                                    color = White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = offer.title,
                                color = White,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = offer.description,
                                color = White.copy(alpha = 0.9f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // CATEGORÍAS

        Column {

            Text(
                text = "Explora nuestro menú",
                modifier = Modifier.padding(horizontal = 22.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Encuentra algo perfecto para ti",
                modifier = Modifier.padding(horizontal = 22.dp),
                fontSize = 12.sp,
                color = SoftBrown
            )

            Spacer(modifier = Modifier.height(15.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(categories) { category ->

                    Surface(
                        modifier = Modifier.clickable {
                            onOpenMenu()
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = White,
                        border = BorderStroke(1.dp, Beige)
                    ) {

                        Text(
                            text = category,
                            modifier = Modifier.padding(
                                horizontal = 15.dp,
                                vertical = 12.dp
                            ),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkText
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // BANNER DESTACADO

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = WineRed
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "UNA EXPERIENCIA ÚNICA",
                        color = White.copy(alpha = 0.8f),
                        fontSize = 9.sp,
                        letterSpacing = 1.2.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "El placer de disfrutar cada bocado.",
                        color = White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 25.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Descubre nuestro menú",
                        color = White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable {
                            onOpenMenu()
                        }
                    )
                }

                Text(
                    text = "🍣",
                    fontSize = 55.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // PRODUCTOS FAVORITOS

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = "Nuestros favoritos",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "Los sabores que no te puedes perder",
                    fontSize = 12.sp,
                    color = SoftBrown
                )
            }

            Text(
                text = "Ver menú",
                color = WineRed,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onOpenMenu()
                }
            )
        }

        Spacer(modifier = Modifier.height(17.dp))

        // LISTA DE PRODUCTOS

        filteredProducts.forEach { product ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .padding(bottom = 13.dp),
                shape = RoundedCornerShape(17.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 1.dp
                )
            ) {

                Row(
                    modifier = Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(65.dp),
                        shape = RoundedCornerShape(14.dp),
                        color = Cream
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = product.emoji,
                                fontSize = 35.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(13.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = product.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = product.description,
                            fontSize = 11.sp,
                            color = SoftBrown,
                            lineHeight = 15.sp
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = "${product.price} MXN",
                            fontSize = 16.sp,
                            color = WineRed,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Button(
                        onClick = {

                            CartManager.addProduct(
                                name = product.name,
                                description = product.description,
                                price = product.price
                                    .removePrefix("$")
                                    .toInt(),
                                emoji = product.emoji
                            )

                            Toast.makeText(
                                context,
                                "${product.name} agregado al carrito",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WineRed
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 11.dp,
                            vertical = 7.dp
                        )
                    ) {

                        Text(
                            text = "+",
                            fontSize = 18.sp,
                            color = White
                        )
                    }
                }
            }
        }

        if (filteredProducts.isEmpty()) {

            Text(
                text = "No encontramos productos.",
                color = SoftBrown,
                modifier = Modifier.padding(
                    horizontal = 22.dp,
                    vertical = 20.dp
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // PIE DE PÁGINA

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 22.dp),
            color = Beige
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "SUSHIGO",
            modifier = Modifier.fillMaxWidth(),
            color = SoftBrown,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sabor japonés en cada momento.",
            modifier = Modifier.fillMaxWidth(),
            color = SoftBrown,
            fontSize = 11.sp
        )
    }
}