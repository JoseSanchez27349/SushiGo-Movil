package com.example.clinetesushigo.data.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.clinetesushigo.data.model.AddCartItemRequest
import com.example.clinetesushigo.data.model.CartItem
import com.example.clinetesushigo.data.model.UpdateCartItemRequest
import com.example.clinetesushigo.data.network.RetrofitClient
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object CartManager {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val cartItems = mutableStateListOf<CartItem>()
    var isSyncing by mutableStateOf(false)
        private set

    fun getTotal(): Int {
        return cartItems.sumOf { it.price * it.quantity }
    }

    suspend fun syncCart() {
        isSyncing = true
        try {
            val response = RetrofitClient.cartApiService.obtenerCarrito()
            if (response.isSuccessful) {
                applyCartJson(response.body())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isSyncing = false
        }
    }

    fun addProduct(
        name: String,
        description: String,
        price: Int,
        emoji: String = "🍣",
        productoId: String = "",
        imageUrl: String? = null,
        quantity: Int = 1
    ) {
        val qtyToAdd = quantity.coerceAtLeast(1)

        val existingIndex = cartItems.indexOfFirst {
            (productoId.isNotBlank() && it.productoId == productoId) ||
                    (productoId.isBlank() && it.name == name)
        }

        if (existingIndex != -1) {
            val current = cartItems[existingIndex]
            cartItems[existingIndex] = current.copy(quantity = current.quantity + qtyToAdd)
        } else {
            cartItems.add(
                CartItem(
                    productoId = productoId,
                    name = name,
                    description = description,
                    price = price,
                    emoji = emoji,
                    imageUrl = imageUrl,
                    quantity = qtyToAdd
                )
            )
        }

        if (productoId.isNotBlank()) {
            scope.launch {
                try {
                    val response = RetrofitClient.cartApiService.agregarItem(
                        AddCartItemRequest(productoId = productoId, cantidad = qtyToAdd)
                    )
                    if (response.isSuccessful) {
                        val updated = applyCartJson(response.body())
                        if (!updated) syncCart()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun increaseQuantity(item: CartItem) {
        val index = cartItems.indexOf(item)
        if (index == -1) return

        val newQty = item.quantity + 1
        cartItems[index] = item.copy(quantity = newQty)

        if (item.id.isNotBlank()) {
            scope.launch {
                try {
                    val response = RetrofitClient.cartApiService.actualizarItem(
                        itemId = item.id,
                        request = UpdateCartItemRequest(cantidad = newQty)
                    )
                    if (response.isSuccessful) {
                        val updated = applyCartJson(response.body())
                        if (!updated) syncCart()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun decreaseQuantity(item: CartItem) {
        val index = cartItems.indexOf(item)
        if (index == -1) return

        if (item.quantity <= 1) {
            removeProduct(item)
            return
        }

        val newQty = item.quantity - 1
        cartItems[index] = item.copy(quantity = newQty)

        if (item.id.isNotBlank()) {
            scope.launch {
                try {
                    val response = RetrofitClient.cartApiService.actualizarItem(
                        itemId = item.id,
                        request = UpdateCartItemRequest(cantidad = newQty)
                    )
                    if (response.isSuccessful) {
                        val updated = applyCartJson(response.body())
                        if (!updated) syncCart()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun removeProduct(item: CartItem) {
        cartItems.remove(item)

        if (item.id.isNotBlank()) {
            scope.launch {
                try {
                    val response = RetrofitClient.cartApiService.eliminarItem(item.id)
                    if (response.isSuccessful) {
                        val updated = applyCartJson(response.body())
                        if (!updated) syncCart()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun applyCartJson(element: JsonElement?): Boolean {
        if (element == null || !element.isJsonObject) return false
        val root = element.asJsonObject

        val cartObj: JsonObject = when {
            root.has("items") && root.get("items").isJsonArray -> root
            root.has("carrito") && root.get("carrito").isJsonObject -> root.getAsJsonObject("carrito")
            root.has("data") && root.get("data").isJsonObject -> root.getAsJsonObject("data")
            else -> return false
        }

        val itemsArray = cartObj.getAsJsonArray("items") ?: return false
        val parsed = mutableListOf<CartItem>()

        for (entry in itemsArray) {
            if (!entry.isJsonObject) continue
            val obj = entry.asJsonObject
            val prodObj = obj.getAsJsonObject("producto")

            val itemId = obj.get("id")?.takeIf { !it.isJsonNull }?.asString.orEmpty()
            val prodId = obj.get("productoId")?.takeIf { !it.isJsonNull }?.asString
                ?: prodObj?.get("id")?.takeIf { !it.isJsonNull }?.asString.orEmpty()
            val qty = obj.get("cantidad")?.takeIf { !it.isJsonNull }?.asInt ?: 1

            val name = prodObj?.get("nombre")?.takeIf { !it.isJsonNull }?.asString ?: "Platillo"
            val desc = prodObj?.get("descripcion")?.takeIf { !it.isJsonNull }?.asString.orEmpty()
            val rawPrice = prodObj?.get("precio")?.takeIf { !it.isJsonNull }?.asDouble
                ?: (obj.get("subtotal")?.takeIf { !it.isJsonNull }?.asDouble?.div(qty.coerceAtLeast(1)) ?: 0.0)
            val img = prodObj?.get("urlImagen")?.takeIf { !it.isJsonNull }?.asString

            parsed.add(
                CartItem(
                    id = itemId,
                    productoId = prodId,
                    name = name,
                    description = desc,
                    price = rawPrice.toInt(),
                    emoji = "🍣",
                    imageUrl = img,
                    quantity = qty
                )
            )
        }

        cartItems.clear()
        cartItems.addAll(parsed)
        return true
    }
}