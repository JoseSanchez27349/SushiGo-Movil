package com.example.clinetesushigo.ui.screens.orders

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clinetesushigo.data.model.Order
import com.example.clinetesushigo.data.repository.OrderManager
import kotlinx.coroutines.launch

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color.White

@Composable
fun OrdersScreen(
    onBack: () -> Unit,
    onOpenCart: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val orders = OrderManager.orders

    // SOLAMENTE MOSTRAR PEDIDOS DEL HISTORIAL
    val historyOrders = orders.filterNot { order ->
        order.rawStatus.lowercase() in listOf(
            "pendiente",
            "pending",
            "en_espera",
            "en_preparacion",
            "listo",
            "preparado",
            "recibido",
            "recibido_en_cocina",
            "processing",
            "ready"
        )
    }

    // CARGAR HISTORIAL AL ENTRAR
    LaunchedEffect(Unit) {
        OrderManager.fetchMyOrders()
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
                .padding(horizontal = 20.dp, vertical = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Historial de pedidos",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Consulta tus compras anteriores",
                    fontSize = 12.sp,
                    color = SoftBrown
                )
            }

            TextButton(
                onClick = {
                    scope.launch {
                        OrderManager.fetchMyOrders()

                        Toast.makeText(
                            context,
                            "Historial actualizado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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

        HorizontalDivider(color = Beige)

        // CARGANDO
        if (OrderManager.isLoading && orders.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = WineRed)
            }

        }

        // HISTORIAL VACÍO
        else if (historyOrders.isEmpty()) {

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
                    text = "Tu historial está vacío",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Cuando completes tus pedidos,\naparecerán aquí tus compras.",
                    fontSize = 14.sp,
                    color = SoftBrown,
                    lineHeight = 21.sp
                )
            }

        }

        // LISTA DEL HISTORIAL
        else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 15.dp
                ),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {

                items(
                    items = historyOrders,
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

// ======================================
// TARJETA DE CADA PEDIDO
// ======================================

@Composable
private fun OrderCard(
    order: Order,
    onReorder: () -> Unit
) {

    val raw = order.rawStatus.lowercase()

    val (badgeBg, badgeText) = when (raw) {

        "entregado", "pagado" -> Pair(
            Color(0xFFE8F5E9),
            Color(0xFF2E7D32)
        )

        "cancelado" -> Pair(
            Color(0xFFFFEBEE),
            Color(0xFFC62828)
        )

        else -> Pair(
            Color(0xFFF5E6E8),
            WineRed
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            // NÚMERO Y ESTADO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = if (order.createdAt.isNotBlank())
                            "COMPRA · ${order.createdAt.uppercase()}"
                        else
                            "NÚMERO DE PEDIDO",
                        fontSize = 10.sp,
                        letterSpacing = 0.8.sp,
                        color = SoftBrown
                    )

                    Spacer(modifier = Modifier.height(4.dp))

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
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 7.dp
                        ),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = badgeText
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 15.dp),
                color = Beige
            )

            // PRODUCTOS
            Text(
                text = "Detalles de compra",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(10.dp))

            order.products.forEach { product ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
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
                        fontWeight = FontWeight.Medium,
                        color = DarkText
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 15.dp),
                color = Beige
            )

            // INFORMACIÓN DE PAGO Y RECOGIDA
            Text(
                text = "Información del pedido",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(12.dp))

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

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 15.dp),
                color = Beige
            )

            // TOTAL
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Total de compra",
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

            // VOLVER A PEDIR
            if (order.products.isNotEmpty()) {

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = onReorder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(11.dp),
                    border = BorderStroke(1.dp, WineRed),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = WineRed
                    )
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

// ======================================
// INFORMACIÓN DEL PEDIDO
// ======================================

@Composable
private fun OrderInformation(
    title: String,
    value: String
) {

    Column(
        modifier = Modifier.widthIn(max = 145.dp)
    ) {

        Text(
            text = title,
            fontSize = 11.sp,
            color = SoftBrown
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value.ifBlank { "No disponible" },
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkText
        )
    }
}