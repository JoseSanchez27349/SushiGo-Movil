package com.example.clinetesushigo.data.model

data class CategoryDto(
    val id: String = "",
    val nombre: String? = "",
    val activa: Boolean? = true
)

data class ProductDto(
    val id: String = "",
    val nombre: String? = "",
    val descripcion: String? = "",
    val precio: Double = 0.0,
    val urlImagen: String? = null,
    val estaDisponible: Boolean? = true,
    val categoriaId: String? = null,
    val categoria: CategoryDto? = null
) {
    fun resolvedCategoryId(): String = categoriaId ?: categoria?.id.orEmpty()
    fun isAvailable(): Boolean = estaDisponible ?: true
}