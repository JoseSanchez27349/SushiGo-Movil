package com.example.clinetesushigo.data.model

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("correoElectronico") val correoElectronico: String
)

data class ForgotPasswordResponse(
    @SerializedName("message") val message: String
)

data class ResetPasswordRequest(
    @SerializedName("token") val token: String,
    @SerializedName("nuevaContrasena") val nuevaContrasena: String
)

data class ResetPasswordResponse(
    @SerializedName("message") val message: String
)