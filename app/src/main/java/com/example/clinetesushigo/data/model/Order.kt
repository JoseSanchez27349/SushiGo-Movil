package com.example.clinetesushigo.data.model

data class Order(
    val id: String = "",
    val orderNumber: String,
    val products: List<CartItem>,
    val total: Int,
    val pickupTime: String,
    val paymentMethod: String,
    val status: String = "Recibido",
    val rawStatus: String = "pendiente",
    val estimatedMinutes: Int = 15,
    val createdAt: String = ""
)