package com.example.clinetesushigo.data.model

data class AddCartItemRequest(
    val productoId: String,
    val cantidad: Int = 1
)

data class UpdateCartItemRequest(
    val cantidad: Int
)

data class CheckoutRequest(
    val metodoPago: String,
    val notas: String? = null
)