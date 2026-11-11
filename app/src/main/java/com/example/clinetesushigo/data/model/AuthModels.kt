package com.example.clinetesushigo.data.model

import com.google.gson.annotations.SerializedName

// Modelo enviado al backend (POST /api/v1/auth/login)
data class LoginRequest(
    @SerializedName("correoElectronico") val correoElectronico: String,
    @SerializedName("contrasena") val contrasena: String
)

// Respuesta devuelta por la API
data class AuthResponse(
    @SerializedName("message") val message: String?,
    @SerializedName("token") val token: String?,
    @SerializedName("usuario") val usuario: UserDto?
)

data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("correoElectronico") val correoElectronico: String?,
    @SerializedName("puntosAcumulados") val puntosAcumulados: Int?
)