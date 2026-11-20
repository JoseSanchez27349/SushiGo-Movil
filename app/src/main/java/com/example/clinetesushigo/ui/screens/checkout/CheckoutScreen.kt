package com.example.clinetesushigo.ui.screens.checkout

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.clinetesushigo.data.repository.OrderManager
import kotlinx.coroutines.launch

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color(0xFFFFFFFF)

@Composable
fun CheckoutScreen(
    onBack: () -> Unit,
    onConfirmOrder: (pickupTime: String, paymentMethod: String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val pickupOptions = listOf(
        "Lo antes posible (15-20 min)",
        "En 30 minutos",
        "En 45 minutos",
        "En 1 hora"
    )

    val paymentOptions = listOf(
        "Efectivo",
        "Tarjeta",
        "Transferencia"
    )

    var selectedPickup by remember { mutableStateOf(pickupOptions.first()) }
    var selectedPayment by remember { mutableStateOf(paymentOptions.first()) }
    var isSubmitting by remember { mutableStateOf(false) }

    val items = CartManager.cartItems
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
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { if (!isSubmitting) onBack() },
                enabled = !isSubmitting
            ) {
                Text(
                    text = "‹",
                    fontSize = 32.sp,
                    color = DarkText
                )
            }

            Column {
                Text(
                    text = "Confirmar pedido",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "Revisa los detalles antes de enviar a cocina",
                    fontSize = 12.sp,
                    color = SoftBrown
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // RESUMEN DE PRODUCTOS
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "RESUMEN DE TU ORDEN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = SoftBrown
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    items.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${item.quantity}x ${item.name}",
                                fontSize = 14.sp,
                                color = DarkText,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = "$${item.price * item.quantity}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DarkText
                            )
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 14.dp),
                        color = Beige
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total a pagar",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )

                        Text(
                            text = "$$total MXN",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = WineRed
                        )
                    }
                }
            }

            // HORARIO DE RECOGIDA
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "HORARIO DE RECOGIDA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = SoftBrown
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    pickupOptions.forEach { option ->
                        SelectableOptionRow(
                            label = option,
                            selected = selectedPickup == option,
                            enabled = !isSubmitting,
                            onClick = { selectedPickup = option }
                        )
                    }
                }
            }

            // MÉTODO DE PAGO
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "MÉTODO DE PAGO AL RECOGER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = SoftBrown
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    paymentOptions.forEach { method ->
                        SelectableOptionRow(
                            label = method,
                            selected = selectedPayment == method,
                            enabled = !isSubmitting,
                            onClick = { selectedPayment = method }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // BOTÓN CONFIRMAR
        Surface(
            color = White,
            shadowElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = {
                        if (isSubmitting || items.isEmpty()) return@Button

                        scope.launch {
                            isSubmitting = true
                            val result = OrderManager.createOrderAsync(
                                pickupTime = selectedPickup,
                                paymentMethod = selectedPayment
                            )
                            isSubmitting = false

                            result.onSuccess {
                                onConfirmOrder(selectedPickup, selectedPayment)
                            }.onFailure { error ->
                                Toast.makeText(
                                    context,
                                    error.message ?: "No se pudo enviar el pedido a cocina",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                    enabled = !isSubmitting && items.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WineRed,
                        disabledContainerColor = WineRed.copy(alpha = 0.5f)
                    )
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(
                            color = White,
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.5.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "ENVIANDO A COCINA...",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    } else {
                        Text(
                            text = "CONFIRMAR PEDIDO · $$total MXN",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectableOptionRow(
    label: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(enabled = enabled, onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = if (selected) WineRed.copy(alpha = 0.08f) else Cream,
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) WineRed else Beige
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (selected) WineRed else DarkText
            )

            RadioButton(
                selected = selected,
                onClick = null,
                colors = RadioButtonDefaults.colors(
                    selectedColor = WineRed,
                    unselectedColor = SoftBrown
                )
            )
        }
    }
}