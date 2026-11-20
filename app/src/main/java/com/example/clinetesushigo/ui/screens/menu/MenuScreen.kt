package com.example.clinetesushigo.ui.screens.menu

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

private data class MenuProduct(
    val name: String,
    val description: String,
    val price: Int,
    val emoji: String,
    val category: String
)

@Composable
fun MenuScreen(
    onBack: () -> Unit,
    onOpenCart: () -> Unit
) {
    val context = LocalContext.current

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }

    val categories = listOf(
        "Todos",
        "Sushi",
        "Entradas",
        "Platillos",
        "Bebidas",
        "Postres"
    )

    val products = listOf(
        MenuProduct("California Roll", "Surimi, pepino y aguacate.", 95, "🍣", "Sushi"),
        MenuProduct("Philadelphia Roll", "Queso crema, salmón y pepino.", 115, "🍣", "Sushi"),
        MenuProduct("Kanikama Roll", "Surimi, aguacate y ajonjolí.", 105, "🍱", "Sushi"),
        MenuProduct("Tampico Roll", "Surimi preparado con salsa tampico.", 110, "🍣", "Sushi"),
        MenuProduct("Gyozas", "Empanaditas japonesas.", 75, "🥟", "Entradas"),
        MenuProduct("Edamames", "Vainas de soya al vapor.", 65, "🫛", "Entradas"),
        MenuProduct("Kushiagues", "Brochetas empanizadas.", 85, "🍢", "Entradas"),
        MenuProduct("Yakimeshi", "Arroz frito estilo japonés.", 85, "🍚", "Platillos"),
        MenuProduct("Ramen", "Caldo japonés con fideos.", 125, "🍜", "Platillos"),
        MenuProduct("Pollo Teriyaki", "Pollo con salsa teriyaki y arroz.", 115, "🍗", "Platillos"),
        MenuProduct("Té verde", "Bebida refrescante.", 35, "🍵", "Bebidas"),
        MenuProduct("Limonada", "Limonada natural.", 40, "🍋", "Bebidas"),
        MenuProduct("Mochi", "Postre japonés suave y dulce.", 55, "🍡", "Postres"),
        MenuProduct("Helado", "Helado de vainilla.", 45, "🍨", "Postres")
    )

    val filteredProducts = products.filter { product ->
        val matchesCategory =
            selectedCategory == "Todos" || product.category == selectedCategory

        val matchesSearch =
            product.name.contains(searchText, ignoreCase = true) ||
                    product.description.contains(searchText, ignoreCase = true)

        matchesCategory && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {

        // Encabezado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "‹",
                    fontSize = 34.sp,
                    color = DarkText,
                    modifier = Modifier.clickable { onBack() }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Nuestro menú",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )

                    Text(
                        text = "Descubre nuestros sabores",
                        fontSize = 12.sp,
                        color = SoftBrown
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .size(43.dp)
                    .clickable { onOpenCart() },
                shape = RoundedCornerShape(50),
                color = White
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "🛒", fontSize = 19.sp)
                }
            }
        }

        // Buscador
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            placeholder = {
                Text("Buscar un producto...", color = SoftBrown)
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WineRed,
                unfocusedBorderColor = Beige,
                focusedContainerColor = White,
                unfocusedContainerColor = White
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Categorías
        Text(
            text = "Categorías",
            modifier = Modifier.padding(horizontal = 20.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(10.dp))

        androidx.compose.foundation.lazy.LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            items(categories) { category ->
                val selected = selectedCategory == category

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (selected) WineRed else White,
                    modifier = Modifier.clickable {
                        selectedCategory = category
                    }
                ) {
                    Text(
                        text = category,
                        modifier = Modifier.padding(
                            horizontal = 17.dp,
                            vertical = 10.dp
                        ),
                        color = if (selected) White else DarkText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${filteredProducts.size} productos disponibles",
            modifier = Modifier.padding(horizontal = 20.dp),
            fontSize = 13.sp,
            color = SoftBrown
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Lista de productos
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                bottom = 25.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredProducts) { product ->

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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Surface(
                            modifier = Modifier.size(68.dp),
                            shape = RoundedCornerShape(15.dp),
                            color = Cream
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = product.emoji,
                                    fontSize = 35.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(13.dp))

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
                                text = "$${product.price} MXN",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = WineRed
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                CartManager.addProduct(
                                    name = product.name,
                                    description = product.description,
                                    price = product.price,
                                    emoji = product.emoji
                                )

                                Toast.makeText(
                                    context,
                                    "${product.name} agregado al carrito",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier.size(39.dp),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WineRed
                            )
                        ) {
                            Text(
                                text = "+",
                                fontSize = 23.sp,
                                color = White
                            )
                        }
                    }
                }
            }

            if (filteredProducts.isEmpty()) {
                item {
                    Text(
                        text = "No encontramos productos.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                        color = SoftBrown
                    )
                }
            }
        }
    }
}