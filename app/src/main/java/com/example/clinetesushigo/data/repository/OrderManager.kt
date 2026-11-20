package com.example.clinetesushigo.data.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.clinetesushigo.data.model.CartItem
import com.example.clinetesushigo.data.model.CheckoutRequest
import com.example.clinetesushigo.data.model.Order
import com.example.clinetesushigo.data.network.RetrofitClient
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

object OrderManager {

    val orders = mutableStateListOf<Order>()
    var isLoading by mutableStateOf(false)
        private set

    private var lastSyncedOrder: Order? = null

    suspend fun fetchMyOrders(silent: Boolean = false) {
        if (!silent) isLoading = true
        try {
            val response = RetrofitClient.orderApiService.obtenerMisOrdenes()
            if (response.isSuccessful) {
                val parsed = parseOrdersResponse(response.body())
                orders.clear()
                orders.addAll(parsed)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (!silent) isLoading = false
        }
    }

    fun reorder(order: Order) {
        order.products.forEach { item ->
            CartManager.addProduct(
                productoId = item.productoId,
                name = item.name,
                description = item.description,
                price = item.price,
                emoji = item.emoji,
                imageUrl = item.imageUrl,
                quantity = item.quantity
            )
        }
    }

    suspend fun createOrderAsync(
        pickupTime: String,
        paymentMethod: String
    ): Result<Order> {
        return try {
            val productsSnapshot = CartManager.cartItems.map { it.copy() }
            val totalSnapshot = CartManager.getTotal()

            val response = RetrofitClient.cartApiService.checkout(
                CheckoutRequest(
                    metodoPago = paymentMethod.lowercase(),
                    notas = "Horario de recogida: $pickupTime"
                )
            )

            if (response.isSuccessful) {
                val root = response.body()?.asJsonObject
                val orderObj = when {
                    root == null -> null
                    root.has("orden") && root.get("orden").isJsonObject -> root.getAsJsonObject("orden")
                    root.has("data") && root.get("data").isJsonObject -> root.getAsJsonObject("data")
                    else -> root
                }

                val orderId = orderObj?.get("id")?.takeIf { !it.isJsonNull }?.asString.orEmpty()
                val folioRaw = orderObj?.get("folio")?.takeIf { !it.isJsonNull }?.asString
                val orderNumber = if (!folioRaw.isNullOrBlank()) {
                    folioRaw
                } else {
                    UUID.randomUUID().toString().take(8).uppercase()
                }

                val rawEstado = orderObj?.get("estado")?.takeIf { !it.isJsonNull }?.asString ?: "pendiente"
                val totalBackend = orderObj?.get("total")?.takeIf { !it.isJsonNull }?.asDouble?.toInt()
                    ?: totalSnapshot

                val order = Order(
                    id = orderId,
                    orderNumber = orderNumber,
                    products = productsSnapshot,
                    total = totalBackend,
                    pickupTime = pickupTime,
                    paymentMethod = paymentMethod,
                    status = mapStatusLabel(rawEstado),
                    rawStatus = rawEstado,
                    estimatedMinutes = 15,
                    createdAt = "Hoy"
                )

                orders.add(0, order)
                lastSyncedOrder = order
                CartManager.cartItems.clear()

                Result.success(order)
            } else {
                val errorRaw = response.errorBody()?.string()
                val msg = try {
                    JSONObject(errorRaw ?: "").getString("message")
                } catch (e: Exception) {
                    "No se pudo procesar el pedido (Error ${response.code()})"
                }
                Result.failure(Exception(msg))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.localizedMessage ?: "Verifica tu red"}"))
        }
    }

    fun createOrder(
        pickupTime: String,
        paymentMethod: String
    ): Order {
        lastSyncedOrder?.let { synced ->
            lastSyncedOrder = null
            return synced
        }

        val products = CartManager.cartItems.map { it.copy() }
        val order = Order(
            orderNumber = UUID.randomUUID().toString().take(8).uppercase(),
            products = products,
            total = CartManager.getTotal(),
            pickupTime = pickupTime,
            paymentMethod = paymentMethod
        )

        orders.add(0, order)
        CartManager.cartItems.clear()
        return order
    }

    private fun parseOrdersResponse(element: JsonElement?): List<Order> {
        if (element == null) return emptyList()

        val array = when {
            element.isJsonArray -> element.asJsonArray
            element.isJsonObject -> {
                val obj = element.asJsonObject
                when {
                    obj.has("ordenes") && obj.get("ordenes").isJsonArray -> obj.getAsJsonArray("ordenes")
                    obj.has("data") && obj.get("data").isJsonArray -> obj.getAsJsonArray("data")
                    else -> return emptyList()
                }
            }
            else -> return emptyList()
        }

        val result = mutableListOf<Order>()
        for (entry in array) {
            if (!entry.isJsonObject) continue
            val obj = entry.asJsonObject

            val id = obj.get("id")?.takeIf { !it.isJsonNull }?.asString.orEmpty()
            val folio = obj.get("folio")?.takeIf { !it.isJsonNull }?.asString
                ?: id.take(8).uppercase()

            val orderEstado = obj.get("estado")?.takeIf { !it.isJsonNull }?.asString ?: "pendiente"
            val kitchenObj = extractKitchenObject(obj)
            val kitchenEstado = kitchenObj?.get("estadoCocina")?.takeIf { !it.isJsonNull }?.asString
            val estimatedMinutes = kitchenObj?.get("tiempoEstimadoMinutos")?.takeIf { !it.isJsonNull }?.asInt ?: 15

            val effectiveRawStatus = resolveEffectiveStatus(orderEstado, kitchenEstado)
            val total = obj.get("total")?.takeIf { !it.isJsonNull }?.asDouble?.toInt() ?: 0
            val notes = obj.get("notas")?.takeIf { !it.isJsonNull }?.asString.orEmpty()
            val createdAtRaw = obj.get("creadoEn")?.takeIf { !it.isJsonNull }?.asString.orEmpty()

            val (pickupTime, paymentMethod) = parseOrderNotes(notes)
            val items = parseOrderItems(obj)

            result.add(
                Order(
                    id = id,
                    orderNumber = folio,
                    products = items,
                    total = total,
                    pickupTime = pickupTime,
                    paymentMethod = paymentMethod,
                    status = mapStatusLabel(effectiveRawStatus),
                    rawStatus = effectiveRawStatus,
                    estimatedMinutes = estimatedMinutes,
                    createdAt = formatOrderDate(createdAtRaw)
                )
            )
        }
        return result
    }

    private fun extractKitchenObject(orderObj: JsonObject): JsonObject? {
        val element = orderObj.get("tiemposCocina") ?: return null
        return when {
            element.isJsonObject -> element.asJsonObject
            element.isJsonArray && element.asJsonArray.size() > 0 && element.asJsonArray[0].isJsonObject ->
                element.asJsonArray[0].asJsonObject
            else -> null
        }
    }

    private fun resolveEffectiveStatus(orderEstado: String, kitchenEstado: String?): String {
        val ord = orderEstado.lowercase()
        val kit = kitchenEstado?.lowercase().orEmpty()

        return when {
            ord == "cancelado" || kit == "cancelado" -> "cancelado"
            ord == "entregado" || ord == "pagado" || kit == "entregado" -> "entregado"
            ord == "listo" || kit == "listo" -> "listo"
            ord == "en_preparacion" || kit == "en_preparacion" -> "en_preparacion"
            else -> "pendiente"
        }
    }

    private fun mapStatusLabel(rawStatus: String): String {
        return when (rawStatus.lowercase()) {
            "cancelado" -> "Cancelado"
            "entregado", "pagado" -> "Entregado"
            "listo" -> "¡Listo para recoger!"
            "en_preparacion" -> "En preparación"
            else -> "Recibido en cocina"
        }
    }

    private fun parseOrderNotes(notes: String): Pair<String, String> {
        var pickup = "Lo antes posible"
        var payment = "Efectivo"

        notes.split("|").map { it.trim() }.forEach { part ->
            when {
                part.startsWith("Horario de recogida:", ignoreCase = true) -> {
                    val value = part.substringAfter(":").trim()
                    if (value.isNotBlank()) pickup = value
                }
                part.contains("Pago:", ignoreCase = true) -> {
                    val value = part.substringAfter("Pago:").trim()
                    if (value.isNotBlank()) {
                        payment = value.replaceFirstChar { it.uppercase() }
                    }
                }
            }
        }
        return Pair(pickup, payment)
    }

    private fun parseOrderItems(orderObj: JsonObject): List<CartItem> {
        val detalles = orderObj.getAsJsonArray("detalles") ?: return emptyList()
        val items = mutableListOf<CartItem>()

        for (entry in detalles) {
            if (!entry.isJsonObject) continue
            val det = entry.asJsonObject
            val prodObj = det.getAsJsonObject("producto")

            val prodId = det.get("productoId")?.takeIf { !it.isJsonNull }?.asString
                ?: prodObj?.get("id")?.takeIf { !it.isJsonNull }?.asString.orEmpty()
            val qty = det.get("cantidad")?.takeIf { !it.isJsonNull }?.asInt ?: 1
            val unitPrice = det.get("precioUnitario")?.takeIf { !it.isJsonNull }?.asDouble?.toInt()
                ?: prodObj?.get("precio")?.takeIf { !it.isJsonNull }?.asDouble?.toInt()
                ?: 0

            val name = prodObj?.get("nombre")?.takeIf { !it.isJsonNull }?.asString ?: "Platillo"
            val desc = prodObj?.get("descripcion")?.takeIf { !it.isJsonNull }?.asString.orEmpty()
            val img = prodObj?.get("urlImagen")?.takeIf { !it.isJsonNull }?.asString

            items.add(
                CartItem(
                    productoId = prodId,
                    name = name,
                    description = desc,
                    price = unitPrice,
                    emoji = "🍣",
                    imageUrl = img,
                    quantity = qty
                )
            )
        }
        return items
    }

    private fun formatOrderDate(isoString: String): String {
        if (isoString.isBlank()) return ""
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val clean = isoString.substringBefore(".")
            val date = parser.parse(clean) ?: return ""
            val formatter = SimpleDateFormat("dd MMM, hh:mm a", Locale("es", "MX"))
            formatter.format(date)
        } catch (e: Exception) {
            ""
        }
    }
}