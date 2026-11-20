package com.example.clinetesushigo.ui.screens.menu

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.clinetesushigo.data.network.RetrofitClient
import com.example.clinetesushigo.data.repository.CartManager
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color(0xFFFFFFFF)

data class MenuProduct(
    val id: String = "",
    val name: String,
    val description: String,
    val price: Int,
    val emoji: String,
    val category: String,
    val protein: String = "Mixto",
    val vegetarian: Boolean = false,
    val spicy: Boolean = false,
    val preparation: String = "15 min",
    val imageUrl: String? = null
)

@Composable
fun MenuScreen(
    onBack: () -> Unit,
    onOpenCart: () -> Unit,
    onOpenProduct: (
        id: String,
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
    ) -> Unit = { _, _, _, _, _, _, _, _, _, _, _ -> }
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }
    var products by remember { mutableStateOf<List<MenuProduct>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    suspend fun loadProducts() {
        isLoading = true
        errorMessage = null
        try {
            val response = RetrofitClient.menuApiService.obtenerProductos()
            if (response.isSuccessful) {
                products = parseProductsJson(response.body())
            } else {
                errorMessage = "No se pudo cargar el menú (Error ${response.code()})"
            }
        } catch (e: Exception) {
            errorMessage = "Error de conexión con el servidor"
        } finally {
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        loadProducts()
    }

    val categories = remember(products) {
        val dynamicCats = products.map { it.category }.filter { it.isNotBlank() }.distinct()
        if (dynamicCats.isEmpty()) {
            listOf("Todos", "Sushi", "Entradas", "Platillos", "Bebidas", "Postres")
        } else {
            listOf("Todos") + dynamicCats
        }
    }

    val filteredProducts = products.filter { product ->
        val matchesCategory =
            selectedCategory == "Todos" || product.category.equals(selectedCategory, ignoreCase = true)

        val matchesSearch =
            product.name.contains(searchText, ignoreCase = true) ||
                    product.description.contains(searchText, ignoreCase = true)

        matchesCategory && matchesSearch
    }

    val totalCartItems = CartManager.cartItems.sumOf { it.quantity }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        // ENCABEZADO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
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
                text = "Menú",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.weight(1f))

            // BOTÓN CARRITO CON CONTADOR
            Surface(
                onClick = onOpenCart,
                shape = RoundedCornerShape(12.dp),
                color = White
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🛒", fontSize = 16.sp)
                    if (totalCartItems > 0) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = totalCartItems.toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = WineRed
                        )
                    }
                }
            }
        }

        // BUSCADOR
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = {
                Text("Buscar rollos, entradas, bebidas...", fontSize = 13.sp, color = SoftBrown)
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedBorderColor = WineRed,
                unfocusedBorderColor = Beige
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        // CATEGORÍAS
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = category },
                    label = {
                        Text(
                            text = category,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = White,
                        labelColor = DarkText,
                        selectedContainerColor = WineRed,
                        selectedLabelColor = White
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = Beige,
                        selectedBorderColor = WineRed
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // CONTENIDO PRINCIPAL
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = WineRed)
                }
            }

            errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = errorMessage.orEmpty(), color = SoftBrown, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { scope.launch { loadProducts() } },
                        colors = ButtonDefaults.buttonColors(containerColor = WineRed)
                    ) {
                        Text("Reintentar")
                    }
                }
            }

            filteredProducts.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontraron platillos",
                        color = SoftBrown,
                        fontSize = 15.sp
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = filteredProducts,
                        key = { it.id.ifBlank { it.name } }
                    ) { product ->
                        MenuProductCard(
                            product = product,
                            onCardClick = {
                                onOpenProduct(
                                    product.id,
                                    product.name,
                                    product.description,
                                    product.price,
                                    product.emoji,
                                    product.category,
                                    product.protein,
                                    product.vegetarian,
                                    product.spicy,
                                    product.preparation,
                                    product.imageUrl.orEmpty()
                                )
                            },
                            onAddClick = {
                                CartManager.addProduct(
                                    productoId = product.id,
                                    name = product.name,
                                    description = product.description,
                                    price = product.price,
                                    emoji = product.emoji,
                                    imageUrl = product.imageUrl
                                )
                                Toast.makeText(
                                    context,
                                    "${product.name} agregado al carrito",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuProductCard(
    product: MenuProduct,
    onCardClick: () -> Unit,
    onAddClick: () -> Unit
) {
    val resolvedImage = RetrofitClient.resolveImageUrl(product.imageUrl)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(13.dp)),
                shape = RoundedCornerShape(13.dp),
                color = Cream
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (!resolvedImage.isNullOrBlank()) {
                        AsyncImage(
                            model = resolvedImage,
                            contentDescription = product.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(text = product.emoji, fontSize = 34.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                if (product.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = product.description,
                        fontSize = 12.sp,
                        color = SoftBrown,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${product.price} MXN",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = WineRed
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Surface(
                onClick = onAddClick,
                shape = CircleShape,
                color = WineRed,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "+",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                }
            }
        }
    }
}

private fun parseProductsJson(element: JsonElement?): List<MenuProduct> {
    if (element == null) return emptyList()

    val array = when {
        element.isJsonArray -> element.asJsonArray
        element.isJsonObject -> {
            val obj = element.asJsonObject
            when {
                obj.has("productos") && obj.get("productos").isJsonArray -> obj.getAsJsonArray("productos")
                obj.has("data") && obj.get("data").isJsonArray -> obj.getAsJsonArray("data")
                obj.has("data") && obj.get("data").isJsonObject -> {
                    val dataObj = obj.getAsJsonObject("data")
                    if (dataObj.has("productos") && dataObj.get("productos").isJsonArray) {
                        dataObj.getAsJsonArray("productos")
                    } else return emptyList()
                }
                else -> return emptyList()
            }
        }
        else -> return emptyList()
    }

    val list = mutableListOf<MenuProduct>()
    for (entry in array) {
        if (!entry.isJsonObject) continue
        val obj = entry.asJsonObject

        val disponible = obj.get("estaDisponible")?.takeIf { !it.isJsonNull }?.asBoolean ?: true
        if (!disponible) continue

        val id = obj.get("id")?.takeIf { !it.isJsonNull }?.asString.orEmpty()
        val name = obj.get("nombre")?.takeIf { !it.isJsonNull }?.asString ?: "Platillo"
        val description = obj.get("descripcion")?.takeIf { !it.isJsonNull }?.asString.orEmpty()
        val price = obj.get("precio")?.takeIf { !it.isJsonNull }?.asDouble?.toInt() ?: 0
        val imageUrl = obj.get("urlImagen")?.takeIf { !it.isJsonNull }?.asString

        val category = extractCategoryName(obj)
        val emoji = when (category.lowercase()) {
            "sushi", "rollos" -> "🍣"
            "entradas" -> "🥟"
            "bebidas" -> "🍵"
            "postres" -> "🍡"
            else -> "🍱"
        }

        val protein = obj.get("proteina")?.takeIf { !it.isJsonNull }?.asString ?: "Mixto"
        val vegetarian = obj.get("esVegetariano")?.takeIf { !it.isJsonNull }?.asBoolean ?: false
        val spicy = obj.get("esPicante")?.takeIf { !it.isJsonNull }?.asBoolean ?: false
        val prepMinutes = obj.get("tiempoPreparacion")?.takeIf { !it.isJsonNull }?.asString ?: "15 min"

        list.add(
            MenuProduct(
                id = id,
                name = name,
                description = description,
                price = price,
                emoji = emoji,
                category = category,
                protein = protein,
                vegetarian = vegetarian,
                spicy = spicy,
                preparation = prepMinutes,
                imageUrl = imageUrl
            )
        )
    }
    return list
}

private fun extractCategoryName(obj: JsonObject): String {
    val catElement = obj.get("categoria") ?: return "Platillos"
    return when {
        catElement.isJsonObject -> catElement.asJsonObject.get("nombre")?.takeIf { !it.isJsonNull }?.asString ?: "Platillos"
        catElement.isJsonPrimitive -> catElement.asString
        else -> "Platillos"
    }
}