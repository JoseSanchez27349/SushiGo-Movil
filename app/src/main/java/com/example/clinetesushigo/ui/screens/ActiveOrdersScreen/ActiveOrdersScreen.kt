package com.example.clinetesushigo.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinetesushigo.data.model.Order
import com.example.clinetesushigo.data.network.SocketManager
import com.example.clinetesushigo.data.repository.OrderManager

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)

@Composable
fun ActiveOrdersScreen(
    onBack: () -> Unit,
    onViewHistory: () -> Unit
) {
    var loading by remember { mutableStateOf(true) }
    var refreshing by remember { mutableStateOf(false) }

    val activeOrders = OrderManager.orders.filter { order ->
        val status = order.rawStatus.lowercase().trim()

        status !in listOf(
            "entregado",
            "pagado",
            "cancelado",
            "completed",
            "delivered",
            "cancelled"
        )
    }

    LaunchedEffect(Unit) {
        loading = true
        OrderManager.fetchMyOrders()
        loading = false
        SocketManager.conectar()
    }

    DisposableEffect(Unit) {
        SocketManager.onOrderStatusChanged = { ordenId, _, _ ->
            val existe = OrderManager.orders.any { it.id == ordenId }

            if (existe) {
                refreshing = true
            }
        }

        onDispose {
            SocketManager.onOrderStatusChanged = null
        }
    }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            OrderManager.fetchMyOrders(silent = true)
            refreshing = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(20.dp)
    ) {

        Text(
            text = "Mis pedidos activos",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Consulta el estado de tus pedidos en tiempo real.",
            color = SoftBrown,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (loading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = WineRed)
            }

        } else if (activeOrders.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("🍣", fontSize = 55.sp)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "No tienes pedidos activos",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Cuando realices un pedido, aparecerá aquí.",
                        color = SoftBrown
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onViewHistory,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WineRed
                        )
                    ) {
                        Text("Ver historial")
                    }
                }
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(
                    items = activeOrders,
                    key = { it.id }
                ) { order ->

                    ActiveOrderCard(order = order)
                }

                item {

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onViewHistory,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Beige,
                            contentColor = DarkText
                        )
                    ) {
                        Text("Consultar historial de pedidos")
                    }
                }
            }
        }
    }
}

@Composable
private fun ActiveOrderCard(order: Order) {

    val status = order.rawStatus.lowercase().trim()

    val currentStep = when (status) {
        "en_preparacion", "preparando" -> 1
        "listo", "preparado" -> 2
        "entregado", "pagado" -> 3
        else -> 0
    }

    val statusText = when (status) {
        "en_preparacion", "preparando" -> "En preparación"
        "listo", "preparado" -> "¡Listo para recoger!"
        "entregado", "pagado" -> "Terminado"
        "cancelado" -> "Cancelado"
        else -> "Pedido recibido"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = "Pedido ${order.orderNumber}",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = statusText,
                color = WineRed,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = Beige)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "PROGRESO DEL PEDIDO",
                color = SoftBrown,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            OrderProgress(currentStep = currentStep)

            Spacer(modifier = Modifier.height(18.dp))

            HorizontalDivider(color = Beige)

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Total: $${order.total}",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = WineRed
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Horario de recogida: ${order.pickupTime}",
                color = SoftBrown,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Método de pago: ${order.paymentMethod}",
                color = SoftBrown,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun OrderProgress(currentStep: Int) {

    val animatedProgress by animateFloatAsState(
        targetValue = (currentStep / 3f).coerceIn(0f, 1f),
        animationSpec = tween(
            durationMillis = 900,
            easing = FastOutSlowInEasing
        ),
        label = "orderProgress"
    )

    val steps = listOf(
        "Recibido",
        "En preparación",
        "Listo para recoger",
        "Entregado"
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        ) {

            val radius = 7.dp.toPx()
            val start = radius
            val end = size.width - radius
            val centerY = size.height / 2

            // Línea de fondo
            drawLine(
                color = Beige,
                start = Offset(start, centerY),
                end = Offset(end, centerY),
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Línea roja animada
            drawLine(
                color = WineRed,
                start = Offset(start, centerY),
                end = Offset(
                    start + (end - start) * animatedProgress,
                    centerY
                ),
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Estaciones
            steps.indices.forEach { index ->

                val x = start + (end - start) * index / 3f

                drawCircle(
                    color = if (index <= currentStep) {
                        WineRed
                    } else {
                        Beige
                    },
                    radius = radius,
                    center = Offset(x, centerY)
                )

                if (index <= currentStep) {
                    drawCircle(
                        color = Color.White,
                        radius = 3.dp.toPx(),
                        center = Offset(x, centerY)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            steps.forEachIndexed { index, step ->

                Text(
                    text = step,
                    modifier = Modifier.weight(1f),
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center,
                    color = if (index <= currentStep) {
                        WineRed
                    } else {
                        SoftBrown
                    },
                    fontWeight = if (index == currentStep) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
                )
            }
        }
    }
}