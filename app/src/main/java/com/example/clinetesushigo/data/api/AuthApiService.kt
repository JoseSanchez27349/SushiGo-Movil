package com.example.clinetesushigo.data.api

import com.example.clinetesushigo.data.model.AuthResponse
import com.example.clinetesushigo.data.model.ForgotPasswordRequest
import com.example.clinetesushigo.data.model.ForgotPasswordResponse
import com.example.clinetesushigo.data.model.LoginRequest
import com.example.clinetesushigo.data.model.RegisterRequest
import com.example.clinetesushigo.data.model.RegisterResponse
import com.example.clinetesushigo.data.model.ResetPasswordRequest
import com.example.clinetesushigo.data.model.ResetPasswordResponse
import com.example.clinetesushigo.data.model.Verify2FARequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("api/v1/auth/customer/login")
    suspend fun loginCliente(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @POST("api/v1/auth/customer/register")
    suspend fun registrarCliente(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/v1/auth/customer/verify-2fa")
    suspend fun verificarCodigo2FA(
        @Body request: Verify2FARequest
    ): Response<AuthResponse>

    //  Nuevos Endpoints de Recuperación
    @POST("api/v1/auth/customer/forgot-password")
    suspend fun solicitarRecuperacion(
        @Body request: ForgotPasswordRequest
    ): Response<ForgotPasswordResponse>

    @POST("api/v1/auth/customer/reset-password")
    suspend fun restablecerContrasena(
        @Body request: ResetPasswordRequest
    ): Response<ResetPasswordResponse>
}