package com.example.clinetesushigo.ui.screens.orders

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinetesushigo.data.model.Order
import com.example.clinetesushigo.data.repository.OrderManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color(0xFFFFFFFF)
private val SuccessGreen = Color(0xFF2E7D32)
private val SuccessBg = Color(0xFFE8F5E9)
private val PrepAmber = Color(0xFFEF6C00)
private val PrepBg = Color(0xFFFFF3E0)

@Composable
fun OrdersScreen(
    onBack: () -> Unit,
    onOpenCart: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val orders = OrderManager.orders

    // Cargar al entrar y actualizar automáticamente cada 15s si hay pedidos en curso
    LaunchedEffect(Unit) {
        OrderManager.fetchMyOrders()
        while (true) {
            delay(15_000)
            val hasActive = OrderManager.orders.any {
                it.rawStatus.lowercase() in listOf("pendiente", "en_espera", "en_preparacion", "listo")
            }
            if (hasActive) {
                OrderManager.fetchMyOrders(silent = true)
            }
        }
    }

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
            TextButton(onClick = onBack) {
                Text(
                    text = "‹",
                    fontSize = 32.sp,
                    color = DarkText
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Mis pedidos",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "Sigue tu orden en vivo o vuelve a pedir",
                    fontSize = 12.sp,
                    color = SoftBrown
                )
            }

            TextButton(
                onClick = {
                    scope.launch { OrderManager.fetchMyOrders() }
                },
                enabled = !OrderManager.isLoading
            ) {
                Text(
                    text = "↻ Actualizar",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = WineRed
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (OrderManager.isLoading && orders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = WineRed)
            }
        } else if (orders.isEmpty()) {
            // HISTORIAL VACÍO
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🍣",
                    fontSize = 65.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Todavía no tienes pedidos",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Cuando realices una compra,\naparecerá aquí tu historial.",
                    fontSize = 14.sp,
                    color = SoftBrown,
                    lineHeight = 21.sp
                )
            }
        } else {
            // LISTA DE PEDIDOS
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 10.dp
                ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(
                    items = orders.toList(),
                    key = { it.id.ifBlank { it.orderNumber } }
                ) { order ->
                    OrderCard(
                        order = order,
                        onReorder = {
                            OrderManager.reorder(order)
                            Toast.makeText(
                                context,
                                "Platillos agregados a tu carrito",
                                Toast.LENGTH_SHORT
                            ).show()
                            onOpenCart()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderCard(
    order: Order,
    onReorder: () -> Unit
) {
    val raw = order.rawStatus.lowercase()
    val isActive = raw in listOf("pendiente", "en_espera", "en_preparacion", "listo")

    val (badgeBg, badgeText) = when (raw) {
        "cancelado" -> Pair(Color(0xFFFFEBEE), Color(0xFFC62828))
        "listo" -> Pair(SuccessBg, SuccessGreen)
        "en_preparacion" -> Pair(PrepBg, PrepAmber)
        "entregado", "pagado" -> Pair(Color(0xFFEEEEEE), SoftBrown)
        else -> Pair(Color(0xFFF5E6E8), WineRed)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // NÚMERO Y ESTADO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (order.createdAt.isNotBlank()) "PEDIDO · ${order.createdAt.uppercase()}" else "NÚMERO DE PEDIDO",
                        fontSize = 10.sp,
                        letterSpacing = 0.8.sp,
                        color = SoftBrown
                    )

                    Text(
                        text = "#${order.orderNumber.removePrefix("#")}",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = WineRed
                    )
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = badgeBg
                ) {
                    Text(
                        text = order.status,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = badgeText
                    )
                }
            }

            // BARRA DE PROGRESO EN VIVO SI ESTÁ EN COCINA
            if (isActive) {
                Spacer(modifier = Modifier.height(16.dp))
                KitchenProgressTracker(
                    rawStatus = raw,
                    estimatedMinutes = order.estimatedMinutes
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 15.dp),
                color = Beige
            )

            // PRODUCTOS
            Text(
                text = "Productos",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(8.dp))

            order.products.forEach { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${product.quantity}x ${product.name}",
                        fontSize = 13.sp,
                        color = SoftBrown,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "$${product.price * product.quantity}",
                        fontSize = 13.sp,
                        color = DarkText
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 15.dp),
                color = Beige
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OrderInformation(
                    title = "Horario de recogida",
                    value = order.pickupTime
                )

                OrderInformation(
                    title = "Método de pago",
                    value = order.paymentMethod
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total del pedido",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Text(
                    text = "$${order.total} MXN",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = WineRed
                )
            }

            if (order.products.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = onReorder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    shape = RoundedCornerShape(11.dp),
                    border = BorderStroke(1.dp, WineRed),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = WineRed)
                ) {
                    Text(
                        text = "Volver a pedir",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun KitchenProgressTracker(
    rawStatus: String,
    estimatedMinutes: Int
) {
    val currentStep = when (rawStatus) {
        "listo" -> 2
        "en_preparacion" -> 1
        else -> 0
    }

    val activeColor = if (currentStep == 2) SuccessGreen else WineRed

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Cream, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = when (currentStep) {
                    2 -> "Tu pedido espera en barra"
                    1 -> "Cocina está preparando tus rollos"
                    else -> "Orden recibida en restaurante"
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkText
            )

            if (currentStep < 2) {
                Text(
                    text = "~$estimatedMinutes min",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = WineRed
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StepDot(active = currentStep >= 0, color = activeColor)
            StepLine(active = currentStep >= 1, color = activeColor, modifier = Modifier.weight(1f))
            StepDot(active = currentStep >= 1, color = activeColor)
            StepLine(active = currentStep >= 2, color = activeColor, modifier = Modifier.weight(1f))
            StepDot(active = currentStep >= 2, color = activeColor)
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Recibido", fontSize = 10.sp, color = if (currentStep >= 0) DarkText else SoftBrown)
            Text("Preparando", fontSize = 10.sp, color = if (currentStep >= 1) DarkText else SoftBrown)
            Text("¡Listo!", fontSize = 10.sp, color = if (currentStep >= 2) SuccessGreen else SoftBrown)
        }
    }
}

@Composable
private fun StepDot(active: Boolean, color: Color) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(if (active) color else Beige)
    )
}

@Composable
private fun StepLine(active: Boolean, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(3.dp)
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(if (active) color else Beige)
    )
}

@Composable
private fun OrderInformation(
    title: String,
    value: String
) {
    Column {
        Text(
            text = title,
            fontSize = 12.sp,
            color = SoftBrown
        )

        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkText
        )
    }
}