package com.example.clinetesushigo.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.clinetesushigo.data.model.Order
import java.util.UUID

object OrderManager {

    val orders = mutableStateListOf<Order>()

    fun createOrder(
        pickupTime: String,
        paymentMethod: String
    ): Order {

        val products = CartManager.cartItems.map { it.copy() }

        val order = Order(
            orderNumber = UUID.randomUUID().toString()
                .take(8).uppercase(),
            products = products,
            total = CartManager.getTotal(),
            pickupTime = pickupTime,
            paymentMethod = paymentMethod
        )

        orders.add(order)
        CartManager.cartItems.clear()

        return order
    }
}