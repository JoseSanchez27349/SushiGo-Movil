package com.example.clinetesushigo.data.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("telefono") val telefono: String,
    @SerializedName("correoElectronico") val correoElectronico: String,
    @SerializedName("contrasena") val contrasena: String,
    @SerializedName("activar2FA") val activar2FA: Boolean = true
)

data class RegisterResponse(
    @SerializedName("message") val message: String,
    @SerializedName("requiereVerificacion") val requiereVerificacion: Boolean = false,
    @SerializedName("usuario") val usuario: UsuarioCreado?
)

data class UsuarioCreado(
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("correoElectronico") val correoElectronico: String
)

data class Verify2FARequest(
    @SerializedName("correoElectronico") val correoElectronico: String,
    @SerializedName("codigo") val codigo: String
)