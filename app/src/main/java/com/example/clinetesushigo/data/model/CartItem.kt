package com.example.clinetesushigo.data.model

data class CartItem(
    val id: String = "",
    val productoId: String = "",
    val name: String,
    val description: String,
    val price: Int,
    val emoji: String = "🍣",
    val imageUrl: String? = null,
    var quantity: Int = 1
)