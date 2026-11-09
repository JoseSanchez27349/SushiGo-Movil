package com.example.clinetesushigo.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
private val White = Color(0xFFFFFFFF)

data class Product(
    val name: String,
    val description: String,
    val price: String,
    val emoji: String
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
            .padding(horizontal = 22.dp, vertical = 25.dp)
    ) {

        // =====================================
        // ENCABEZADO
        // =====================================

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "BIENVENIDO A",
                    fontSize = 11.sp,
                    letterSpacing = 3.sp,
                    color = SoftBrown,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "SushiGo",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "Una experiencia de sabor japonés",
                    fontSize = 12.sp,
                    color = SoftBrown
                )
            }

            // PERFIL
            Surface(
                onClick = onOpenProfile,
                shape = CircleShape,
                color = White,
                shadowElevation = 1.dp,
                modifier = Modifier.size(43.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text("👤", fontSize = 19.sp)
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // CARRITO
            Surface(
                onClick = onOpenCart,
                shape = CircleShape,
                color = White,
                shadowElevation = 1.dp,
                modifier = Modifier.size(43.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text("🛒", fontSize = 19.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // =====================================
        // BUSCADOR
        // =====================================

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Buscar platillo...",
                    color = SoftBrown,
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Text(
                    "⌕",
                    fontSize = 24.sp,
                    color = SoftBrown
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
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

        Spacer(modifier = Modifier.height(25.dp))

        // =====================================
        // BANNER PRINCIPAL
        // =====================================

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
                    text = "EXPERIENCIA SUSHIGO",
                    color = White.copy(alpha = 0.8f),
                    fontSize = 10.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "El arte de disfrutar\ncada bocado.",
                    color = White,
                    fontSize = 25.sp,
                    lineHeight = 31.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Descubre nuestros sabores japoneses.",
                    color = White.copy(alpha = 0.85f),
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "SUSHIGO  •  SABOR Y TRADICIÓN",
                    color = White,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // =====================================
        // BOTÓN DE PROMOCIONES
        // =====================================

        OutlinedButton(
            onClick = onOpenPromotions,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = WineRed
            ),
            border = BorderStroke(
                1.dp,
                WineRed
            )
        ) {

            Text(
                text = "VER PROMOCIONES Y OFERTAS",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // =====================================
        // BOTÓN MENÚ COMPLETO
        // =====================================

        Button(
            onClick = onOpenMenu,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = WineRed
            )
        ) {

            Text(
                text = "VER MENÚ COMPLETO",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // =====================================
        // PRODUCTOS FAVORITOS
        // =====================================

        Text(
            text = "Nuestros favoritos",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Seleccionados especialmente para ti",
            fontSize = 12.sp,
            color = SoftBrown
        )

        Spacer(modifier = Modifier.height(17.dp))

        // =====================================
        // TARJETAS DE PRODUCTOS
        // =====================================

        filteredProducts.forEach { product ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
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

                    // IMAGEN DEL PRODUCTO
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

                    // INFORMACIÓN
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

                    // AGREGAR AL CARRITO
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

        // =====================================
        // MENSAJE SIN RESULTADOS
        // =====================================

        if (filteredProducts.isEmpty()) {

            Text(
                text = "No encontramos productos.",
                color = SoftBrown,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // =====================================
        // PIE DE PÁGINA
        // =====================================

        HorizontalDivider(color = Beige)

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "SUSHIGO",
            modifier = Modifier.fillMaxWidth(),
            color = SoftBrown,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}