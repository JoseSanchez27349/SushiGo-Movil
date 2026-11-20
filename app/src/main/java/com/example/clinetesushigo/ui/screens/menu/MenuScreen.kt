package com.example.clinetesushigo.ui.screens.menu

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.clinetesushigo.data.model.CategoryDto
import com.example.clinetesushigo.data.model.ProductDto
import com.example.clinetesushigo.data.network.RetrofitClient
import com.example.clinetesushigo.data.repository.CartManager
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color(0xFFFFFFFF)

@Composable
fun MenuScreen(
    onBack: () -> Unit,
    onOpenCart: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var searchText by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf("ALL") }

    var categories by remember {
        mutableStateOf(listOf(CategoryDto(id = "ALL", nombre = "Todos")))
    }
    var products by remember { mutableStateOf<List<ProductDto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    suspend fun cargarMenu() {
        isLoading = true
        errorMessage = null
        try {
            coroutineScope {
                val catDeferred = async { RetrofitClient.menuApiService.obtenerCategorias() }
                val prodDeferred = async { RetrofitClient.menuApiService.obtenerProductos() }

                val catResponse = catDeferred.await()
                val prodResponse = prodDeferred.await()

                if (catResponse.isSuccessful && prodResponse.isSuccessful) {
                    val activas = extractList<CategoryDto>(
                        catResponse.body(),
                        "categorias",
                        "data"
                    ).filter { it.activa ?: true }

                    categories = listOf(CategoryDto(id = "ALL", nombre = "Todos")) + activas
                    products = extractList(
                        prodResponse.body(),
                        "productos",
                        "data"
                    )

                    if (categories.none { it.id == selectedCategoryId }) {
                        selectedCategoryId = "ALL"
                    }
                } else {
                    errorMessage = "Error del servidor (Cat: ${catResponse.code()}, Prod: ${prodResponse.code()})"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = "Error al cargar menú: ${e.javaClass.simpleName} - ${e.localizedMessage ?: "Sin detalle"}"
        } finally {
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        cargarMenu()
    }

    val filteredProducts = products.filter { product ->
        val matchesCategory =
            selectedCategoryId == "ALL" || product.resolvedCategoryId() == selectedCategoryId

        val nombreSafe = product.nombre.orEmpty()
        val descSafe = product.descripcion.orEmpty()

        val matchesSearch =
            nombreSafe.contains(searchText, ignoreCase = true) ||
                    descSafe.contains(searchText, ignoreCase = true)

        product.isAvailable() && matchesCategory && matchesSearch
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

        // Categorías dinámicas desde el Admin
        Text(
            text = "Categorías",
            modifier = Modifier.padding(horizontal = 20.dp),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            itemsIndexed(
                items = categories,
                key = { index, category -> "${category.id}_$index" }
            ) { _, category ->
                val selected = selectedCategoryId == category.id

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (selected) WineRed else White,
                    modifier = Modifier.clickable {
                        selectedCategoryId = category.id
                    }
                ) {
                    Text(
                        text = category.nombre.orEmpty(),
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

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = WineRed)
                }
            }

            errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = errorMessage!!,
                        color = SoftBrown,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { scope.launch { cargarMenu() } },
                        colors = ButtonDefaults.buttonColors(containerColor = WineRed)
                    ) {
                        Text("Reintentar", color = White)
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 25.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(
                        items = filteredProducts,
                        key = { index, product -> "${product.id}_$index" }
                    ) { _, product ->
                        val resolvedImage = RetrofitClient.resolveImageUrl(product.urlImagen)

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .size(68.dp)
                                        .clip(RoundedCornerShape(15.dp)),
                                    shape = RoundedCornerShape(15.dp),
                                    color = Cream
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        if (!resolvedImage.isNullOrBlank()) {
                                            AsyncImage(
                                                model = resolvedImage,
                                                contentDescription = product.nombre,
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            Text(text = "🍣", fontSize = 35.sp)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.width(13.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = product.nombre.orEmpty(),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DarkText
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = product.descripcion.orEmpty().ifBlank { "Especialidad SushiGo" },
                                        fontSize = 11.sp,
                                        color = SoftBrown,
                                        lineHeight = 15.sp
                                    )

                                    Spacer(modifier = Modifier.height(7.dp))

                                    val formattedPrice = if (product.precio % 1.0 == 0.0) {
                                        "${product.precio.toInt()}"
                                    } else {
                                        String.format("%.2f", product.precio)
                                    }

                                    Text(
                                        text = "$$formattedPrice MXN",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = WineRed
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = {

                                        CartManager.addProduct(
                                            productoId = product.id,
                                            name = product.nombre.orEmpty(),
                                            description = product.descripcion.orEmpty(),
                                            price = product.precio.toInt(),
                                            emoji = "🍣",
                                            imageUrl = product.urlImagen
                                        )

                                        Toast.makeText(
                                            context,
                                            "${product.nombre} agregado al carrito",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    },
                                    modifier = Modifier.size(39.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = WineRed)
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
                                text = "No encontramos productos en esta categoría.",
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
    }
}

// Extrae la lista tanto si viene como [...] directo o envuelta en { "categorias"/"productos"/"data": [...] }
private inline fun <reified T> extractList(element: JsonElement?, vararg keys: String): List<T> {
    if (element == null || element.isJsonNull) return emptyList()
    val array = when {
        element.isJsonArray -> element.asJsonArray
        element.isJsonObject -> {
            val obj = element.asJsonObject
            keys.firstNotNullOfOrNull { key ->
                obj.get(key)?.takeIf { it.isJsonArray }?.asJsonArray
            }
                ?: obj.entrySet().firstOrNull { it.value.isJsonArray }?.value?.asJsonArray
                ?: obj.entrySet()
                    .firstOrNull { it.value.isJsonObject }
                    ?.value?.asJsonObject
                    ?.entrySet()
                    ?.firstOrNull { it.value.isJsonArray }
                    ?.value?.asJsonArray
        }
        else -> null
    } ?: return emptyList()

    val type = object : TypeToken<List<T>>() {}.type
    return Gson().fromJson(array, type) ?: emptyList()
}