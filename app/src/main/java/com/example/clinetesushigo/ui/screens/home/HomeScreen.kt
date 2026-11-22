package com.example.clinetesushigo.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.clinetesushigo.R
import com.example.clinetesushigo.data.network.RetrofitClient
import com.example.clinetesushigo.data.repository.CartManager
import com.example.clinetesushigo.ui.screens.menu.MenuProduct
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

private val Cream = Color(0xFFF8F5F0)
private val WineRed = Color(0xFF9E3038)
private val DarkText = Color(0xFF252525)
private val SoftBrown = Color(0xFF81776D)
private val Beige = Color(0xFFE9E0D5)
private val White = Color.White

data class Offer(
    val title: String,
    val description: String,
    val imageUrl: String,
    val label: String
)

@Composable
fun HomeScreen(
    onOpenCart: () -> Unit,
    onOpenMenu: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenPromotions: () -> Unit,
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

    var search by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }

    var products by remember {
        mutableStateOf<List<MenuProduct>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    // PROMOCIONES ACTUALES
    val offers = listOf(
        Offer(
            title = "Sushi para compartir",
            description = "Disfruta tus rollos favoritos.",
            imageUrl = "https://images.unsplash.com/photo-1579871494447-9811cf80d66c",
            label = "FAVORITOS"
        ),
        Offer(
            title = "Una experiencia japonesa",
            description = "Descubre sabores únicos.",
            imageUrl = "https://images.unsplash.com/photo-1579584425555-c3ce17fd4351",
            label = "RECOMENDACIÓN"
        ),
        Offer(
            title = "Momentos deliciosos",
            description = "El sabor perfecto para hoy.",
            imageUrl = "https://images.unsplash.com/photo-1553621042-f6e147245754",
            label = "ESPECIAL"
        )
    )

    // CARGAR PRODUCTOS DEL BACKEND
    suspend fun loadProducts() {

        isLoading = true
        errorMessage = null

        try {

            val response =
                RetrofitClient.menuApiService.obtenerProductos()

            if (response.isSuccessful) {

                products = parseHomeProducts(response.body())

            } else {

                errorMessage =
                    "No se pudieron cargar los platillos. Error ${response.code()}"

            }

        } catch (e: Exception) {

            errorMessage = "No se pudo conectar con el servidor."

        } finally {

            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        loadProducts()
    }

    // CATEGORÍAS DINÁMICAS
    val categories = remember(products) {

        val dynamicCategories = products
            .map { it.category }
            .filter { it.isNotBlank() }
            .distinct()

        listOf("Todos") + dynamicCategories
    }

    // FILTRADO DE PRODUCTOS
    val filteredProducts = products.filter { product ->

        val matchesSearch =
            product.name.contains(search, ignoreCase = true) ||
                    product.description.contains(search, ignoreCase = true)

        val matchesCategory =
            selectedCategory == "Todos" ||
                    product.category.equals(
                        selectedCategory,
                        ignoreCase = true
                    )

        matchesSearch && matchesCategory
    }

    // CONTENIDO PRINCIPAL
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 25.dp)
    ) {

        // ENCABEZADO
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(top = 25.dp)
        ) {

            Text(
                text = "¡Hola! 👋",
                fontSize = 14.sp,
                color = SoftBrown
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "¿Qué se te antoja hoy?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "Descubre el auténtico sabor de Japón.",
                fontSize = 12.sp,
                color = SoftBrown
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // BUSCADOR
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            placeholder = {
                Text(
                    "Buscar sushi, bebidas, entradas...",
                    fontSize = 13.sp,
                    color = SoftBrown
                )
            },
            leadingIcon = {
                Text(
                    "⌕",
                    fontSize = 25.sp,
                    color = SoftBrown
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = White,
                focusedContainerColor = White,
                unfocusedBorderColor = Beige,
                focusedBorderColor = WineRed,
                focusedTextColor = DarkText,
                unfocusedTextColor = DarkText,
                cursorColor = WineRed
            )
        )

        Spacer(modifier = Modifier.height(27.dp))

        // PROMOCIONES
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = "Ofertas especiales",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Text(
                    text = "Algo delicioso te está esperando",
                    fontSize = 12.sp,
                    color = SoftBrown
                )
            }

            Text(
                text = "Ver todas",
                color = WineRed,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onOpenPromotions()
                }
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // TARJETAS DE PROMOCIONES
        LazyRow(
            contentPadding = PaddingValues(horizontal = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(offers) { offer ->

                Card(
                    modifier = Modifier
                        .width(280.dp)
                        .height(170.dp)
                        .clickable {
                            onOpenPromotions()
                        },
                    shape = RoundedCornerShape(19.dp)
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        AsyncImage(
                            model = offer.imageUrl,
                            contentDescription = offer.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.85f)
                                        )
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {

                            Surface(
                                color = WineRed,
                                shape = RoundedCornerShape(6.dp)
                            ) {

                                Text(
                                    text = offer.label,
                                    modifier = Modifier.padding(
                                        horizontal = 9.dp,
                                        vertical = 5.dp
                                    ),
                                    color = White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(7.dp))

                            Text(
                                text = offer.title,
                                color = White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = offer.description,
                                color = White,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(27.dp))

        // BANNER DESTACADO CON IMAGEN DE SUSHIGO
        Image(
            painter = painterResource(
                id = R.drawable.banner_experiencia
            ),
            contentDescription = "Una experiencia única. El placer de disfrutar cada bocado. Prepara tu próximo pedido.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(19.dp))
                .clickable {
                    onOpenPromotions()
                },
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(27.dp))

        // MENÚ INTEGRADO
        Column(
            modifier = Modifier.padding(horizontal = 22.dp)
        ) {

            Text(
                text = "Nuestro menú",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Encuentra tu próximo platillo favorito.",
                fontSize = 12.sp,
                color = SoftBrown
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // FILTROS DE CATEGORÍAS
        LazyRow(
            contentPadding = PaddingValues(horizontal = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {

            items(categories) { category ->

                val selected = selectedCategory == category

                FilterChip(
                    selected = selected,
                    onClick = {
                        selectedCategory = category
                    },
                    label = {
                        Text(
                            text = category,
                            fontSize = 12.sp
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
                        selected = selected,
                        borderColor = Beige,
                        selectedBorderColor = WineRed
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(17.dp))

        // PRODUCTOS DEL BACKEND
        when {

            isLoading -> {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color = WineRed
                    )
                }
            }

            errorMessage != null -> {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = errorMessage.orEmpty(),
                        color = SoftBrown,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                loadProducts()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WineRed
                        )
                    ) {

                        Text("Reintentar")
                    }
                }
            }

            filteredProducts.isEmpty() -> {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "No encontramos platillos.",
                        color = SoftBrown,
                        fontSize = 14.sp
                    )
                }
            }

            else -> {

                // TODOS LOS PLATILLOS
                filteredProducts.forEach { product ->

                    HomeProductCard(
                        product = product,
                        onProductClick = {

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

        Spacer(modifier = Modifier.height(25.dp))

        // PIE DE PÁGINA
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 22.dp),
            color = Beige
        )

        Spacer(modifier = Modifier.height(17.dp))

        Text(
            text = "SUSHIGO",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            color = SoftBrown,
            fontSize = 11.sp,
            letterSpacing = 3.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "Sabor japonés en cada momento.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            color = SoftBrown,
            fontSize = 11.sp
        )
    }
}

// TARJETA DE PRODUCTO
@Composable
private fun HomeProductCard(
    product: MenuProduct,
    onProductClick: () -> Unit,
    onAddClick: () -> Unit
) {

    val resolvedImage =
        RetrofitClient.resolveImageUrl(product.imageUrl)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 22.dp,
                vertical = 6.dp
            )
            .clickable {
                onProductClick()
            },
        shape = RoundedCornerShape(16.dp),
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
                .padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // IMAGEN DEL PRODUCTO
            Surface(
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(13.dp)),
                shape = RoundedCornerShape(13.dp),
                color = Cream
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    if (!resolvedImage.isNullOrBlank()) {

                        AsyncImage(
                            model = resolvedImage,
                            contentDescription = product.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                    } else {

                        Text(
                            text = product.emoji,
                            fontSize = 34.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(13.dp))

            // INFORMACIÓN DEL PRODUCTO
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
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
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

            // AGREGAR AL CARRITO
            Surface(
                onClick = onAddClick,
                shape = CircleShape,
                color = WineRed,
                modifier = Modifier.size(36.dp)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "+",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                }
            }
        }
    }
}

// CONVERTIR RESPUESTA DEL BACKEND
private fun parseHomeProducts(
    element: JsonElement?
): List<MenuProduct> {

    if (element == null) return emptyList()

    val array = when {

        element.isJsonArray -> element.asJsonArray

        element.isJsonObject -> {

            val obj = element.asJsonObject

            when {

                obj.has("productos") &&
                        obj.get("productos").isJsonArray ->
                    obj.getAsJsonArray("productos")

                obj.has("data") &&
                        obj.get("data").isJsonArray ->
                    obj.get("data").asJsonArray

                obj.has("data") &&
                        obj.get("data").isJsonObject -> {

                    val dataObj = obj.getAsJsonObject("data")

                    if (
                        dataObj.has("productos") &&
                        dataObj.get("productos").isJsonArray
                    ) {

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

        val disponible =
            obj.get("estaDisponible")
                ?.takeIf { !it.isJsonNull }
                ?.asBoolean ?: true

        if (!disponible) continue

        val id =
            obj.get("id")
                ?.takeIf { !it.isJsonNull }
                ?.asString.orEmpty()

        val name =
            obj.get("nombre")
                ?.takeIf { !it.isJsonNull }
                ?.asString ?: "Platillo"

        val description =
            obj.get("descripcion")
                ?.takeIf { !it.isJsonNull }
                ?.asString.orEmpty()

        val price =
            obj.get("precio")
                ?.takeIf { !it.isJsonNull }
                ?.asDouble?.toInt() ?: 0

        val imageUrl =
            obj.get("urlImagen")
                ?.takeIf { !it.isJsonNull }
                ?.asString

        val category = extractHomeCategory(obj)

        val emoji = when (category.lowercase()) {

            "sushi", "rollos" -> "🍣"
            "entradas" -> "🥟"
            "bebidas" -> "🍵"
            "postres" -> "🍡"
            "ramen" -> "🍜"
            "yakimeshi" -> "🍚"

            else -> "🍱"
        }

        val protein =
            obj.get("proteina")
                ?.takeIf { !it.isJsonNull }
                ?.asString ?: "Mixto"

        val vegetarian =
            obj.get("esVegetariano")
                ?.takeIf { !it.isJsonNull }
                ?.asBoolean ?: false

        val spicy =
            obj.get("esPicante")
                ?.takeIf { !it.isJsonNull }
                ?.asBoolean ?: false

        val preparation =
            obj.get("tiempoPreparacion")
                ?.takeIf { !it.isJsonNull }
                ?.asString ?: "15 min"

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
                preparation = preparation,
                imageUrl = imageUrl
            )
        )
    }

    return list
}

// OBTENER CATEGORÍA
private fun extractHomeCategory(
    obj: JsonObject
): String {

    val category = obj.get("categoria")
        ?: return "Platillos"

    return when {

        category.isJsonObject -> {

            category.asJsonObject
                .get("nombre")
                ?.takeIf { !it.isJsonNull }
                ?.asString ?: "Platillos"
        }

        category.isJsonPrimitive -> category.asString

        else -> "Platillos"
    }
}