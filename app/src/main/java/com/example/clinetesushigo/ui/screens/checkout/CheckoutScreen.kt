package com.example.clinetesushigo.ui.screens.checkout

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

@Composable
fun CheckoutScreen(
    onBack: () -> Unit,
    onConfirmOrder: (String, String) -> Unit
) {

    var pickupTime by remember {
        mutableStateOf("Lo antes posible")
    }

    var paymentMethod by remember {
        mutableStateOf("Efectivo")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

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
                .padding(horizontal = 22.dp, vertical = 18.dp),
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
                text = "Finalizar compra",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
        }

        HorizontalDivider(color = Beige)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 20.dp)
        ) {

            // TÍTULO
            Text(
                text = "Casi listo...",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Revisa tu pedido y selecciona cómo deseas recogerlo.",
                fontSize = 13.sp,
                color = SoftBrown
            )

            Spacer(modifier = Modifier.height(25.dp))

            // RESUMEN
            Text(
                text = "Resumen de tu pedido",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    CartManager.cartItems.forEach { product ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 9.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = product.emoji,
                                fontSize = 27.sp
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = product.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DarkText
                                )

                                Text(
                                    text = "Cantidad: ${product.quantity}",
                                    fontSize = 12.sp,
                                    color = SoftBrown
                                )
                            }

                            Text(
                                text = "$${product.price * product.quantity}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )
                        }
                    }

                    HorizontalDivider(color = Beige)

                    Spacer(modifier = Modifier.height(13.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Total a pagar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )

                        Text(
                            text = "$${total} MXN",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = WineRed
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(27.dp))

            // TIPO DE ENTREGA
            Text(
                text = "Tipo de entrega",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = White
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(17.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "",
                        fontSize = 30.sp
                    )

                    Spacer(modifier = Modifier.width(13.dp))

                    Column {

                        Text(
                            text = "Recoger en restaurante",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Recoge tu pedido directamente en SushiGo.",
                            fontSize = 12.sp,
                            color = SoftBrown
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(27.dp))

            // HORARIO
            Text(
                text = "Horario de recogida",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box {

                OutlinedButton(
                    onClick = {
                        expanded = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = White,
                        contentColor = DarkText
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        Beige
                    ),
                    contentPadding = PaddingValues(16.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = " $pickupTime",
                            fontSize = 14.sp
                        )

                        Text(
                            text = "⌄",
                            fontSize = 20.sp,
                            color = WineRed
                        )
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                    modifier = Modifier.background(White)
                ) {

                    listOf(
                        "Lo antes posible",
                        "En 30 minutos",
                        "En 45 minutos",
                        "En 1 hora"
                    ).forEach { time ->

                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = time,
                                    color = DarkText
                                )
                            },
                            onClick = {
                                pickupTime = time
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(27.dp))

            // MÉTODO DE PAGO
            Text(
                text = "Método de pago",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(12.dp))

            // OPCIÓN EFECTIVO
            PaymentOption(
                title = "Efectivo",
                description = "Paga al recoger tu pedido.",
                emoji = "💵",
                selected = paymentMethod == "Efectivo",
                onClick = {
                    paymentMethod = "Efectivo"
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // OPCIÓN TARJETA
            PaymentOption(
                title = "Tarjeta",
                description = "Pago con tarjeta.",
                emoji = "💳",
                selected = paymentMethod == "Tarjeta",
                onClick = {
                    paymentMethod = "Tarjeta"
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        // BOTÓN FINAL
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 22.dp, vertical = 17.dp)
        ) {

            Button(
                onClick = {
                    if (CartManager.cartItems.isNotEmpty()) {
                        onConfirmOrder(
                            pickupTime,
                            paymentMethod
                        )
                    }
                },
                enabled = CartManager.cartItems.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WineRed
                ),
                shape = RoundedCornerShape(13.dp)
            ) {

                Text(
                    text = "CONFIRMAR PEDIDO",
                    fontSize = 13.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// COMPONENTE DE MÉTODO DE PAGO
@Composable
fun PaymentOption(
    title: String,
    description: String,
    emoji: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                Color(0xFFF2E8E4)
            else
                White
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (selected) WineRed else Beige
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = emoji,
                fontSize = 27.sp
            )

            Spacer(modifier = Modifier.width(13.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = SoftBrown
                )
            }

            RadioButton(
                selected = selected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = WineRed
                )
            )
        }
    }
}