package com.example.clinetesushigo.data.model

data class CartItem(
    val name: String,
    val description: String,
    val price: Int,
    val emoji: String,
    var quantity: Int = 1
)