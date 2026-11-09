package com.example.clinetesushigo.ui.screens.confirmation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.clinetesushigo.data.model.Order

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color(0xFFFFFFFF)

@Composable
fun ConfirmationScreen(
    order: Order,
    onGoHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Icono de confirmación
        Surface(
            modifier = Modifier.size(90.dp),
            shape = CircleShape,
            color = Color(0xFFE8F0E8)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "✓",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF54845B)
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "¡Pedido confirmado!",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Gracias por elegir SushiGo.",
            fontSize = 15.sp,
            color = SoftBrown
        )

        Text(
            text = "Estamos preparando todo para ti.",
            fontSize = 14.sp,
            color = SoftBrown
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Tarjeta de información
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(22.dp)
            ) {

                Text(
                    text = "RESUMEN DEL PEDIDO",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftBrown,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "#${order.orderNumber}",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = WineRed
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 18.dp),
                    color = Beige
                )

                ConfirmationDetail(
                    title = "Estado del pedido",
                    value = order.status
                )

                Spacer(modifier = Modifier.height(17.dp))

                ConfirmationDetail(
                    title = "Horario de recogida",
                    value = order.pickupTime
                )

                Spacer(modifier = Modifier.height(17.dp))

                ConfirmationDetail(
                    title = "Método de pago",
                    value = order.paymentMethod
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 18.dp),
                    color = Beige
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total del pedido",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkText
                    )

                    Text(
                        text = "$${order.total} MXN",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = WineRed
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Presenta tu número de pedido al recogerlo.",
            fontSize = 13.sp,
            color = SoftBrown
        )

        Spacer(modifier = Modifier.height(22.dp))

        Button(
            onClick = onGoHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = WineRed
            )
        ) {
            Text(
                text = "REGRESAR AL INICIO",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
private fun ConfirmationDetail(
    title: String,
    value: String
) {
    Column {
        Text(
            text = title,
            fontSize = 13.sp,
            color = SoftBrown
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkText
        )
    }
}