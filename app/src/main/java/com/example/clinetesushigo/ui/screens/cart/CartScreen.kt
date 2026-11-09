package com.example.clinetesushigo.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinetesushigo.data.model.CartItem
import com.example.clinetesushigo.data.repository.CartManager

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color(0xFFFFFFFF)

@Composable
fun CartScreen(
    onBack: () -> Unit,
    onContinueShopping: () -> Unit
) {

    val products = CartManager.cartItems
    val total = CartManager.getTotal()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {

        // ENCABEZADO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextButton(
                onClick = onBack,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "← Regresar",
                    color = WineRed,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Mi carrito",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "🛒",
                fontSize = 20.sp
            )
        }

        HorizontalDivider(
            color = Beige,
            thickness = 1.dp
        )

        // CARRITO VACÍO
        if (products.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Surface(
                    modifier = Modifier.size(110.dp),
                    shape = CircleShape,
                    color = Beige
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🛒",
                            fontSize = 48.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Tu carrito está vacío",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Descubre nuestros platillos favoritos.",
                    fontSize = 13.sp,
                    color = SoftBrown
                )

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WineRed
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(
                        horizontal = 28.dp,
                        vertical = 12.dp
                    )
                ) {
                    Text("Explorar menú")
                }
            }

        } else {

            // LISTA DE PRODUCTOS
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 18.dp
                ),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {

                item {
                    Text(
                        text = "Tus selecciones",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Revisa tus platillos antes de continuar.",
                        fontSize = 12.sp,
                        color = SoftBrown
                    )
                }

                items(products.toList()) { product ->

                    CartProductItem(product = product)
                }
            }

            // RESUMEN DE COMPRA
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 22.dp, vertical = 20.dp)
            ) {

                Text(
                    text = "Resumen de compra",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Subtotal",
                        fontSize = 14.sp,
                        color = SoftBrown
                    )

                    Text(
                        text = "$${total} MXN",
                        fontSize = 14.sp,
                        color = DarkText
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                HorizontalDivider(color = Beige)

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Total",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    Text(
                        text = "$${total} MXN",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = WineRed
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // BOTÓN CONTINUAR
                Button(
                    onClick = onContinueShopping,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WineRed
                    ),
                    shape = RoundedCornerShape(13.dp)
                ) {

                    Text(
                        text = "CONTINUAR CON LA COMPRA",
                        fontSize = 13.sp,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// TARJETA DE PRODUCTO
@Composable
fun CartProductItem(product: CartItem) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        shape = RoundedCornerShape(17.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // IMAGEN DEL PRODUCTO
            Surface(
                modifier = Modifier.size(65.dp),
                shape = RoundedCornerShape(13.dp),
                color = Cream
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = product.emoji,
                        fontSize = 34.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

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
                    text = "$${product.price} MXN",
                    fontSize = 14.sp,
                    color = WineRed,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(9.dp))

                // CONTROL DE CANTIDAD
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        onClick = {
                            CartManager.decreaseQuantity(product)
                        },
                        shape = RoundedCornerShape(7.dp),
                        color = Cream,
                        modifier = Modifier.size(30.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "−",
                                fontSize = 18.sp,
                                color = DarkText
                            )
                        }
                    }

                    Text(
                        text = product.quantity.toString(),
                        modifier = Modifier.padding(horizontal = 13.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    Surface(
                        onClick = {
                            CartManager.increaseQuantity(product)
                        },
                        shape = RoundedCornerShape(7.dp),
                        color = Cream,
                        modifier = Modifier.size(30.dp)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+",
                                fontSize = 18.sp,
                                color = DarkText
                            )
                        }
                    }
                }
            }

            // ELIMINAR
            TextButton(
                onClick = {
                    CartManager.removeProduct(product)
                },
                contentPadding = PaddingValues(4.dp)
            ) {

                Text(
                    text = "Eliminar",
                    color = SoftBrown,
                    fontSize = 11.sp
                )
            }
        }
    }
}