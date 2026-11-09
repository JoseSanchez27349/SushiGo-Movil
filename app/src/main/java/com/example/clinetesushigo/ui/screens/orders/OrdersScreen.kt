package com.example.clinetesushigo.ui.screens.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.clinetesushigo.data.repository.OrderManager

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color(0xFFFFFFFF)

@Composable
fun OrdersScreen(
    onBack: () -> Unit
) {

    val orders = OrderManager.orders

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

            Column {
                Text(
                    text = "Mis pedidos",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "Consulta tus compras anteriores",
                    fontSize = 12.sp,
                    color = SoftBrown
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (orders.isEmpty()) {

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
                    items = orders.reversed(),
                    key = { it.orderNumber }
                ) { order ->

                    OrderCard(order = order)
                }
            }
        }
    }
}

@Composable
private fun OrderCard(order: Order) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
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
                        text = "NÚMERO DE PEDIDO",
                        fontSize = 10.sp,
                        letterSpacing = 1.sp,
                        color = SoftBrown
                    )

                    Text(
                        text = "#${order.orderNumber}",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = WineRed
                    )
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFE8F0E8)
                ) {
                    Text(
                        text = order.status,
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 7.dp
                        ),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF54845B)
                    )
                }
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
                        color = SoftBrown
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

            OrderInformation(
                title = "Horario de recogida",
                value = order.pickupTime
            )

            Spacer(modifier = Modifier.height(10.dp))

            OrderInformation(
                title = "Método de pago",
                value = order.paymentMethod
            )

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
        }
    }
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