package com.example.clinetesushigo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.clinetesushigo.ui.screens.ActiveOrdersScreen
import com.example.clinetesushigo.ui.screens.orders.OrdersScreen

private val WineRed = Color(0xFF9E3038)
private val Cream = Color(0xFFF8F5F0)
private val SoftBrown = Color(0xFF81776D)

@Composable
fun MyOrdersScreen(
    onBack: () -> Unit,
    onOpenCart: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {

        Text(
            text = "Mis pedidos",
            modifier = Modifier.padding(
                start = 20.dp,
                top = 20.dp,
                bottom = 12.dp
            ),
            color = WineRed,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Cream,
            contentColor = WineRed
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Pedidos activos") }
            )

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Historial") }
            )
        }

        when (selectedTab) {
            0 -> ActiveOrdersScreen(
                onBack = onBack,
                onViewHistory = {
                    selectedTab = 1
                }
            )

            1 -> OrdersScreen(
                onBack = onBack,
                onOpenCart = onOpenCart
            )
        }
    }
}