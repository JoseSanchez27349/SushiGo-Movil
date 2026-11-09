package com.example.clinetesushigo.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.clinetesushigo.data.model.CartItem

object CartManager {

    val cartItems = mutableStateListOf<CartItem>()

    fun addProduct(
        name: String,
        description: String,
        price: Int,
        emoji: String
    ) {
        val existingItem = cartItems.find { it.name == name }

        if (existingItem != null) {
            val index = cartItems.indexOf(existingItem)
            cartItems[index] = existingItem.copy(
                quantity = existingItem.quantity + 1
            )
        } else {
            cartItems.add(
                CartItem(
                    name = name,
                    description = description,
                    price = price,
                    emoji = emoji
                )
            )
        }
    }

    fun increaseQuantity(item: CartItem) {
        val index = cartItems.indexOf(item)

        if (index != -1) {
            cartItems[index] = item.copy(
                quantity = item.quantity + 1
            )
        }
    }

    fun decreaseQuantity(item: CartItem) {
        val index = cartItems.indexOf(item)

        if (index != -1) {
            if (item.quantity > 1) {
                cartItems[index] = item.copy(
                    quantity = item.quantity - 1
                )
            } else {
                cartItems.removeAt(index)
            }
        }
    }

    fun removeProduct(item: CartItem) {
        cartItems.remove(item)
    }

    fun getTotal(): Int {
        return cartItems.sumOf {
            it.price * it.quantity
        }
    }
}