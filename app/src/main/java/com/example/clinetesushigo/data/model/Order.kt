package com.example.clinetesushigo.data.model

data class Order(
    val orderNumber: String,
    val products: List<CartItem>,
    val total: Int,
    val pickupTime: String,
    val paymentMethod: String,
    val status: String = "Recibido"
)