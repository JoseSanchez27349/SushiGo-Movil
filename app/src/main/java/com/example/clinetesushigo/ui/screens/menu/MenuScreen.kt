package com.example.clinetesushigo.ui.screens.menu

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
    val category: String,
    val protein: String,
    val vegetarian: Boolean,
    val spicy: Boolean,
    val preparation: String
) {
    val imageUrl: String
        get() = mapOf(
            "California Roll" to "https://loremflickr.com/600/400/california,sushi?lock=1",
            "Philadelphia Roll" to "https://loremflickr.com/600/400/salmon,sushi?lock=2",
            "Kanikama Roll" to "https://loremflickr.com/600/400/crab,sushi?lock=3",
            "Tampico Roll" to "https://loremflickr.com/600/400/tampico,sushi?lock=4",
            "Spicy Tuna Roll" to "https://loremflickr.com/600/400/spicy,tuna,sushi?lock=5",
            "Ebi Roll" to "https://loremflickr.com/600/400/shrimp,tempura,sushi?lock=6",
            "Vegetarian Roll" to "https://loremflickr.com/600/400/vegetable,sushi?lock=7",
            "Dragón Roll" to "https://loremflickr.com/600/400/dragon,sushi,roll?lock=8",
            "Empanizado Roll" to "https://loremflickr.com/600/400/tempura,sushi?lock=9",
            "Ramen de Pollo" to "https://loremflickr.com/600/400/chicken,ramen?lock=10",
            "Ramen de Res" to "https://loremflickr.com/600/400/beef,ramen?lock=11",
            "Ramen de Camarón" to "https://loremflickr.com/600/400/shrimp,ramen?lock=12",
            "Ramen Picante" to "https://loremflickr.com/600/400/spicy,ramen?lock=13",
            "Ramen Vegetariano" to "https://loremflickr.com/600/400/vegetarian,ramen?lock=14",
            "Yakimeshi de Pollo" to "https://loremflickr.com/600/400/chicken,fried,rice?lock=15",
            "Yakimeshi de Res" to "https://loremflickr.com/600/400/beef,fried,rice?lock=16",
            "Yakimeshi Mixto" to "https://loremflickr.com/600/400/japanese,fried,rice?lock=17",
            "Yakimeshi Vegetariano" to "https://loremflickr.com/600/400/vegetable,fried,rice?lock=18",
            "Gyozas de Pollo" to "https://loremflickr.com/600/400/chicken,gyoza?lock=19",
            "Gyozas de Verduras" to "https://loremflickr.com/600/400/vegetable,gyoza?lock=20",
            "Edamames" to "https://loremflickr.com/600/400/edamame?lock=21",
            "Kushiagues de Queso" to "https://loremflickr.com/600/400/cheese,tempura?lock=22",
            "Rollitos Primavera" to "https://loremflickr.com/600/400/spring,rolls?lock=23",
            "Kushiagues de Camarón" to "https://loremflickr.com/600/400/shrimp,tempura?lock=24",
            "Pollo Teriyaki" to "https://loremflickr.com/600/400/chicken,teriyaki?lock=25",
            "Chicken Katsu" to "https://loremflickr.com/600/400/chicken,katsu?lock=26",
            "Res con Verduras" to "https://loremflickr.com/600/400/beef,vegetables,asian?lock=27",
            "Camarones Empanizados" to "https://loremflickr.com/600/400/fried,shrimp?lock=28",
            "Boneless Picantes" to "https://loremflickr.com/600/400/spicy,chicken,wings?lock=29",
            "Pollo Agridulce" to "https://loremflickr.com/600/400/sweet,sour,chicken?lock=30",
            "Té Verde" to "https://loremflickr.com/600/400/green,tea?lock=31",
            "Limonada" to "https://loremflickr.com/600/400/lemonade,drink?lock=32",
            "Refresco" to "https://loremflickr.com/600/400/soft,drink?lock=33",
            "Mochi" to "https://loremflickr.com/600/400/mochi,dessert?lock=34",
            "Helado de Té Verde" to "https://loremflickr.com/600/400/matcha,ice,cream?lock=35",
            "Dorayaki" to "https://loremflickr.com/600/400/dorayaki,japanese?lock=36"
        )[name] ?: ""
}

@Composable
fun MenuScreen(
    onBack: () -> Unit,
    onOpenCart: () -> Unit,
    onOpenProduct: (
        name: String,
        description: String,
        price: Int,
        emoji: String,
        category: String,
        protein: String,
        vegetarian: Boolean,
        spicy: Boolean,
        preparation: String,
        imageUrl: String
    ) -> Unit = { _, _, _, _, _, _, _, _, _, _ -> }
) {
    val context = LocalContext.current

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }
    var selectedProtein by remember { mutableStateOf("Todos") }
    var selectedSpicy by remember { mutableStateOf("Todos") }
    var selectedPreparation by remember { mutableStateOf("Todos") }

    val categories = listOf(
        "Todos", "Sushi", "Ramen", "Arroces",
        "Entradas", "Platillos", "Bebidas", "Postres"
    )

    val proteins = listOf(
        "Todos", "Pollo", "Res", "Camarón", "Pescado", "Vegetariano"
    )

    val spicyOptions = listOf(
        "Todos", "Picosos", "Sin picante"
    )

    val preparations = listOf(
        "Todos", "Empanizado", "Asado", "Frito",
        "Fresco", "Cocido", "Natural"
    )

    val products = listOf(

        // SUSHI
        MenuProduct("California Roll", "Surimi, pepino, aguacate y ajonjolí.", 95, "🍣", "Sushi", "Pescado", false, false, "Fresco"),
        MenuProduct("Philadelphia Roll", "Salmón, queso crema y pepino.", 115, "🍣", "Sushi", "Pescado", false, false, "Fresco"),
        MenuProduct("Kanikama Roll", "Surimi, aguacate y ajonjolí.", 105, "🍱", "Sushi", "Pescado", false, false, "Fresco"),
        MenuProduct("Tampico Roll", "Surimi preparado con salsa tampico.", 110, "🍣", "Sushi", "Pescado", false, true, "Fresco"),
        MenuProduct("Spicy Tuna Roll", "Atún con salsa ligeramente picante.", 125, "🌶️", "Sushi", "Pescado", false, true, "Fresco"),
        MenuProduct("Ebi Roll", "Camarón empanizado, aguacate y queso crema.", 130, "🍤", "Sushi", "Camarón", false, false, "Empanizado"),
        MenuProduct("Vegetarian Roll", "Aguacate, pepino, zanahoria y verduras.", 95, "🥑", "Sushi", "Vegetariano", true, false, "Fresco"),
        MenuProduct("Dragón Roll", "Camarón, aguacate y salsa especial.", 145, "🐉", "Sushi", "Camarón", false, true, "Fresco"),
        MenuProduct("Empanizado Roll", "Rollo de sushi empanizado con queso crema.", 120, "🍣", "Sushi", "Pescado", false, false, "Empanizado"),

        // RAMEN
        MenuProduct("Ramen de Pollo", "Caldo japonés, fideos, pollo y verduras.", 135, "🍜", "Ramen", "Pollo", false, false, "Cocido"),
        MenuProduct("Ramen de Res", "Caldo tradicional con res y vegetales.", 145, "🍜", "Ramen", "Res", false, false, "Cocido"),
        MenuProduct("Ramen de Camarón", "Fideos japoneses con camarones y verduras.", 155, "🍜", "Ramen", "Camarón", false, false, "Cocido"),
        MenuProduct("Ramen Picante", "Caldo especial con chile, fideos y pollo.", 145, "🌶️", "Ramen", "Pollo", false, true, "Cocido"),
        MenuProduct("Ramen Vegetariano", "Caldo de verduras, tofu y champiñones.", 125, "🥬", "Ramen", "Vegetariano", true, false, "Cocido"),

        // ARROCES
        MenuProduct("Yakimeshi de Pollo", "Arroz frito con pollo y verduras.", 95, "🍚", "Arroces", "Pollo", false, false, "Frito"),
        MenuProduct("Yakimeshi de Res", "Arroz japonés con res y vegetales.", 110, "🍚", "Arroces", "Res", false, false, "Frito"),
        MenuProduct("Yakimeshi Mixto", "Arroz frito con pollo, res y camarón.", 135, "🍚", "Arroces", "Camarón", false, false, "Frito"),
        MenuProduct("Yakimeshi Vegetariano", "Arroz con zanahoria, calabaza y brócoli.", 85, "🥕", "Arroces", "Vegetariano", true, false, "Frito"),

        // ENTRADAS
        MenuProduct("Gyozas de Pollo", "Empanaditas japonesas rellenas de pollo.", 85, "🥟", "Entradas", "Pollo", false, false, "Frito"),
        MenuProduct("Gyozas de Verduras", "Empanaditas rellenas de vegetales.", 75, "🥟", "Entradas", "Vegetariano", true, false, "Frito"),
        MenuProduct("Edamames", "Vainas de soya al vapor con sal.", 65, "🫛", "Entradas", "Vegetariano", true, false, "Cocido"),
        MenuProduct("Kushiagues de Queso", "Brochetas de queso empanizadas.", 85, "🍢", "Entradas", "Vegetariano", true, false, "Empanizado"),
        MenuProduct("Rollitos Primavera", "Rollitos crujientes rellenos de verduras.", 75, "🥢", "Entradas", "Vegetariano", true, false, "Frito"),
        MenuProduct("Kushiagues de Camarón", "Brochetas de camarón empanizadas.", 110, "🍤", "Entradas", "Camarón", false, false, "Empanizado"),

        // PLATILLOS
        MenuProduct("Pollo Teriyaki", "Pollo a la plancha con salsa teriyaki y arroz.", 125, "🍗", "Platillos", "Pollo", false, false, "Asado"),
        MenuProduct("Chicken Katsu", "Pollo empanizado estilo japonés con arroz.", 135, "🍗", "Platillos", "Pollo", false, false, "Empanizado"),
        MenuProduct("Res con Verduras", "Tiras de res salteadas con vegetales.", 145, "🥩", "Platillos", "Res", false, false, "Frito"),
        MenuProduct("Camarones Empanizados", "Camarones crujientes con aderezo especial.", 155, "🍤", "Platillos", "Camarón", false, false, "Empanizado"),
        MenuProduct("Boneless Picantes", "Trozos de pollo bañados en salsa picante.", 125, "🌶️", "Platillos", "Pollo", false, true, "Frito"),
        MenuProduct("Pollo Agridulce", "Pollo crujiente con salsa agridulce.", 130, "🍗", "Platillos", "Pollo", false, false, "Frito"),

        // BEBIDAS
        MenuProduct("Té Verde", "Té verde japonés refrescante.", 35, "🍵", "Bebidas", "Vegetariano", true, false, "Natural"),
        MenuProduct("Limonada", "Limonada natural preparada al momento.", 40, "🍋", "Bebidas", "Vegetariano", true, false, "Natural"),
        MenuProduct("Refresco", "Refresco frío de diferentes sabores.", 35, "🥤", "Bebidas", "Vegetariano", true, false, "Natural"),

        // POSTRES
        MenuProduct("Mochi", "Postre japonés suave y dulce.", 55, "🍡", "Postres", "Vegetariano", true, false, "Natural"),
        MenuProduct("Helado de Té Verde", "Helado cremoso con sabor a matcha.", 60, "🍨", "Postres", "Vegetariano", true, false, "Natural"),
        MenuProduct("Dorayaki", "Panqueques japoneses rellenos de dulce.", 65, "🥞", "Postres", "Vegetariano", true, false, "Natural")
    )

    val filteredProducts = products.filter { product ->
        val matchesCategory =
            selectedCategory == "Todos" || product.category == selectedCategory

        val matchesProtein =
            selectedProtein == "Todos" || product.protein == selectedProtein

        val matchesSpicy =
            selectedSpicy == "Todos" ||
                    (selectedSpicy == "Picosos" && product.spicy) ||
                    (selectedSpicy == "Sin picante" && !product.spicy)

        val matchesPreparation =
            selectedPreparation == "Todos" ||
                    product.preparation == selectedPreparation

        val matchesSearch =
            product.name.contains(searchText, ignoreCase = true) ||
                    product.description.contains(searchText, ignoreCase = true)

        matchesCategory && matchesProtein && matchesSpicy &&
                matchesPreparation && matchesSearch
    }

    val productRows = filteredProducts.chunked(2)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream),
        contentPadding = PaddingValues(bottom = 25.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                Text(
                    text = "Nuestro menú",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Descubre los sabores de Japón y Oriente",
                    fontSize = 13.sp,
                    color = SoftBrown
                )
            }
        }

        item {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                placeholder = {
                    Text(
                        "Buscar sushi, ramen, pollo...",
                        fontSize = 14.sp,
                        color = SoftBrown
                    )
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
        }

        item {
            Column {
                Text(
                    "Categorías",
                    modifier = Modifier.padding(horizontal = 18.dp),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(9.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    items(categories) { category ->
                        FilterOption(
                            text = category,
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category }
                        )
                    }
                }
            }
        }

        item {
            Column {
                Text(
                    "Filtrar por alimento",
                    modifier = Modifier.padding(horizontal = 18.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(9.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    items(proteins) { protein ->
                        val label = when (protein) {
                            "Pollo" -> "🍗 Pollo"
                            "Res" -> "🥩 Res"
                            "Camarón" -> "🍤 Camarón"
                            "Pescado" -> "🐟 Pescado"
                            "Vegetariano" -> "🥑 Vegetariano"
                            else -> protein
                        }

                        FilterOption(
                            text = label,
                            selected = selectedProtein == protein,
                            onClick = { selectedProtein = protein }
                        )
                    }
                }
            }
        }

        item {
            Column {
                Text(
                    "Nivel de picante",
                    modifier = Modifier.padding(horizontal = 18.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    items(spicyOptions) { option ->
                        FilterOption(
                            text = when (option) {
                                "Picosos" -> "🌶️ Picosos"
                                "Sin picante" -> "Sin picante"
                                else -> option
                            },
                            selected = selectedSpicy == option,
                            onClick = { selectedSpicy = option }
                        )
                    }
                }
            }
        }

        item {
            Column {
                Text(
                    "Tipo de preparación",
                    modifier = Modifier.padding(horizontal = 18.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    items(preparations) { preparation ->
                        FilterOption(
                            text = preparation,
                            selected = selectedPreparation == preparation,
                            onClick = { selectedPreparation = preparation }
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${filteredProducts.size} platillos disponibles",
                    fontSize = 12.sp,
                    color = SoftBrown
                )

                Text(
                    "Limpiar filtros",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = WineRed,
                    modifier = Modifier.clickable {
                        selectedCategory = "Todos"
                        selectedProtein = "Todos"
                        selectedSpicy = "Todos"
                        selectedPreparation = "Todos"
                        searchText = ""
                    }
                )
            }
        }

        items(productRows) { rowProducts ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                rowProducts.forEach { product ->
                    ProductCard(
                        product = product,
                        onOpenDetails = {
                            onOpenProduct(
                                product.name,
                                product.description,
                                product.price,
                                product.emoji,
                                product.category,
                                product.protein,
                                product.vegetarian,
                                product.spicy,
                                product.preparation,
                                product.imageUrl
                            )
                        },
                        onAdd = {
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
                        modifier = Modifier.weight(1f)
                    )
                }

                if (rowProducts.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        if (filteredProducts.isEmpty()) {
            item {
                Text(
                    "No encontramos platillos con esos filtros.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    color = SoftBrown,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun ProductCard(
    product: MenuProduct,
    onOpenDetails: () -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onOpenDetails() },
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Surface(
                    modifier = Modifier
                        .padding(7.dp)
                        .align(Alignment.TopEnd),
                    shape = RoundedCornerShape(8.dp),
                    color = Cream.copy(alpha = 0.95f)
                ) {
                    Text(
                        text = product.category,
                        modifier = Modifier.padding(
                            horizontal = 7.dp,
                            vertical = 4.dp
                        ),
                        fontSize = 9.sp,
                        color = WineRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(modifier = Modifier.padding(11.dp)) {
                Text(
                    text = product.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    maxLines = 2,
                    minLines = 1
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = product.description,
                    fontSize = 10.sp,
                    color = SoftBrown,
                    lineHeight = 14.sp,
                    maxLines = 3,
                    minLines = 2
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = when {
                        product.spicy -> "🌶️ Picante"
                        product.vegetarian -> "🥑 Vegetariano"
                        else -> product.protein
                    },
                    fontSize = 10.sp,
                    color = WineRed,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$${product.price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = WineRed
                    )

                    Button(
                        onClick = onAdd,
                        modifier = Modifier.size(34.dp),
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WineRed
                        )
                    ) {
                        Text(
                            text = "+",
                            fontSize = 21.sp,
                            color = White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (selected) WineRed else White,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 9.dp
            ),
            color = if (selected) White else DarkText,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}